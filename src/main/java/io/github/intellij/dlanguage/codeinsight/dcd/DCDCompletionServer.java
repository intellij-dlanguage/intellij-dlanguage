package io.github.intellij.dlanguage.codeinsight.dcd;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.process.KillableProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessOutputType;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.PathUtil;
import com.intellij.util.io.BaseOutputReader;
import io.github.intellij.dlanguage.messagebus.ToolChangeListener;
import io.github.intellij.dlanguage.messagebus.Topics;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.settings.ToolSettings;
import io.github.intellij.dlanguage.DlangSdkType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.github.intellij.dlanguage.utils.DUtil.isNotNullOrEmpty;

/**
 * Process wrapper for DCD Server.  Implements ModuleComponent so destruction of processes coincides with closing projects.
 */
public final class DCDCompletionServer implements ToolChangeListener, Disposable, DumbAware {

    private static final Logger LOG = Logger.getInstance(DCDCompletionServer.class);

    @NotNull
    public Module module;

    @Nullable
    public String path;

    @NotNull
    public String flags;

    @Nullable
    private KillableProcessHandler processHandler;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Package Private constructor used during module component initialization.
     */
    DCDCompletionServer(@NotNull Module module) {
        this.module = module;
        this.path = lookupPath();
        this.flags = lookupFlags();

        // Ensure that we are notified of changes to the settings.
        ApplicationManager.getApplication().getMessageBus().connect().subscribe(Topics.DCD_SERVER_TOOL_CHANGE, this);
    }

    public boolean isExecutable() {
        return StringUtil.isNotEmpty(path);
    }

    public boolean isRunning() {
        return StringUtil.isNotEmpty(path) && processHandler != null && processHandler.getProcess().isAlive();
    }

    public synchronized void exec() {
        if (path != null) {
            if (processHandler == null) {
                spawnProcess();
            }
        }
    }

    private void spawnProcess() {
        if(StringUtil.isEmptyOrSpaces(this.path)) {
            LOG.warn("request made to spawn process for DCD Server but path is not set");
            return;
        }
        final GeneralCommandLine cmd = buildDcdCommand(this.path);

        try {
            LOG.info("DCD server starting...\n" + cmd.getCommandLineString());

            this.processHandler = new DCDServerProcessHandler(cmd);

            this.processHandler.addProcessListener(new ProcessAdapter() {
                @Override
                public void onTextAvailable(@NotNull ProcessEvent event, @NotNull Key outputType) {
                    if(ProcessOutputType.isStdout(outputType)) {
                        if(StringUtil.contains(event.getText(), "[warning]")) {
                            LOG.warn(StringUtil.trimTrailing(event.getText()));
                        } else {
                            LOG.info(StringUtil.trimTrailing(event.getText()));
                        }
                    } else if(ProcessOutputType.isStderr(outputType)) {
                        LOG.error(StringUtil.trimTrailing(event.getText()));
                    }
                }
            });
            this.processHandler.startNotify();

            LOG.info("DCD process started");
        } catch (final ExecutionException e) {
            Notifications.Bus.notify(new Notification("DCDNotification", "DCD Error",
                "Unable to start a dcd server. Make sure that you have specified the path to the dcd-server and dcd-client executables correctly. You can specify executable paths under File > Settings > Languages & Frameworks > D Tools",
                NotificationType.ERROR), null);
            LOG.error("Error spawning DCD process", e);
        }
    }

    // package private for unit testing
    GeneralCommandLine buildDcdCommand(@NotNull final String path) {
        @Nullable final VirtualFile projectRoot = ProjectUtil.guessProjectDir(this.module.getProject());

        @Nullable final String rootDirPath = projectRoot != null && projectRoot.exists() ?
            projectRoot.getCanonicalPath() :
            this.module.getProject().getBasePath();

        final GeneralCommandLine commandLine = new GeneralCommandLine(path)
            .withWorkDirectory(PathUtil.toSystemDependentName(rootDirPath))
            .withRedirectErrorStream(true)
            .withParameters("--ignoreConfig", "--logLevel", "all");

        final ParametersList parametersList = commandLine.getParametersList();

        if (isNotNullOrEmpty(flags)) {
            parametersList.addAll(flags);
        }

        // try to auto add project files in source root
        Arrays.stream(ProjectUtil.getRootManager(module).getSourceRoots(false))
            .forEach(root -> {
                if(StringUtil.isNotEmpty(root.getCanonicalPath())) {
                    parametersList.add("-I", PathUtil.toSystemDependentName(root.getCanonicalPath()));
                }
            });

        // try to auto add the compiler sources
        final List<String> compilerSources = getCompilerSourcePaths();

        if(compilerSources.isEmpty()) {
            LOG.warn("compiler sources not passed to DCD Server. Has a D compiler been setup for this project?");
        }

        compilerSources.forEach(src -> {
            parametersList.add("-I", PathUtil.toSystemDependentName(src));
        });

        Arrays.stream(LibraryTablesRegistrar.getInstance().getLibraryTable(module.getProject()).getLibraries())
            .forEach(library -> {
                Arrays.stream(library.getRootProvider().getUrls(OrderRootType.CLASSES)).forEach(
                    url -> {
                        try {
                            parametersList.add("-I", URI.create(url).toURL().getPath());
                        } catch (MalformedURLException ignored) {
                        }
                    });
            });

        return commandLine;
    }

    private List<String> getCompilerSourcePaths() {
        final ArrayList<String> compilerSourcePaths = new ArrayList<>();
        final ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        final Sdk sdk = moduleRootManager.getSdk();

        if (sdk != null && (sdk.getSdkType() instanceof DlangSdkType)) {
            Arrays.stream(sdk.getRootProvider().getFiles(OrderRootType.SOURCES))
                .forEach(f -> {
                    if (f.exists() && f.isDirectory()) {
                        compilerSourcePaths.add(f.getPath());
                    }
                });
        }
        return compilerSourcePaths;
    }

    @Nullable
    private String lookupPath() {
        return ToolKey.DCD_SERVER_KEY.getPath();
    }

    @NotNull
    private String lookupFlags() {
        return ToolKey.DCD_SERVER_KEY.getFlags();
    }

    /**
     * Kills the existing process and closes input and output if they exist.
     */
    private synchronized void kill() {
        if (processHandler != null) {
            LOG.info("Shutting down DCD Server...");
            processHandler.destroyProcess();
            processHandler.killProcess();
        }
        processHandler = null;
    }

    /**
     * Restarts the dcd-server.
     */
    public synchronized void restart() {
        kill();
        if(isNotNullOrEmpty(path)) {
            try {
                Thread.sleep(1500);
                spawnProcess();
            } catch (final InterruptedException e) {
                LOG.error(e);
            }
        }
    }

    @Override
    public void onToolSettingsChanged(@NotNull final ToolSettings settings) {
        LOG.debug("DCD Server settings changed");
        kill();
        this.path = settings.getPath();
        this.flags = settings.getFlags();
        if(isNotNullOrEmpty(this.path)) {
            restart();
        }
    }

    @Override
    public void dispose() {
        LOG.debug("Disposing DCD Completion Server component");
        executorService.shutdownNow();
        kill();
    }

    /**
     * Extending KillableProcessHandler so that we can override readerOptions()
     */
    private static class DCDServerProcessHandler extends KillableProcessHandler {
        public DCDServerProcessHandler(@NotNull GeneralCommandLine commandLine) throws ExecutionException {
            super(commandLine);
        }

        @Override
        protected BaseOutputReader.@NotNull Options readerOptions() {
            return BaseOutputReader.Options.forMostlySilentProcess();
        }
    }
}
