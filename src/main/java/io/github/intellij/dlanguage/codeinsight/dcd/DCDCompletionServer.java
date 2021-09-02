package io.github.intellij.dlanguage.codeinsight.dcd;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleComponent;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.intellij.dlanguage.messagebus.ToolChangeListener;
import io.github.intellij.dlanguage.messagebus.Topics;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.settings.ToolSettings;
import io.github.intellij.dlanguage.DlangSdkType;
import io.github.intellij.dlanguage.project.DubConfigurationParser;
import io.github.intellij.dlanguage.utils.NotificationUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.github.intellij.dlanguage.utils.DUtil.isNotNullOrEmpty;

/**
 * Process wrapper for DCD Server.  Implements ModuleComponent so destruction of processes coincides with closing projects.
 */
public final class DCDCompletionServer implements ModuleComponent, ToolChangeListener {

    private static final Logger LOG = Logger.getInstance(DCDCompletionServer.class);

    @NotNull
    public final Module module;

    @NotNull
    private final String workingDirectory;

    @Nullable
    public String path;

    @NotNull
    public String flags;

    @Nullable
    private Process process;

    @Nullable
    private BufferedReader input;

    @Nullable
    private BufferedWriter output;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Private constructor used during module component initialization.
     */
    public DCDCompletionServer(@NotNull final Module module) {
        this.module = module;
        this.path = lookupPath();
        this.flags = lookupFlags();
        this.workingDirectory = ModuleUtil.getModuleDirPath(module);

        // Ensure that we are notified of changes to the settings.
        module.getProject().getMessageBus().connect().subscribe(Topics.DCD_SERVER_TOOL_CHANGE, this);
    }

    private static void displayError(@NotNull final Project project, @NotNull final String message) {
        NotificationUtil.displayToolsNotification(NotificationType.ERROR, project, "dcd error", message);
    }

    public synchronized void exec() throws DCDError {
        if (path != null) {
            if (process == null) {
                spawnProcess();
            }
            if (output == null) {
                throw new InitError("Output stream was unexpectedly null.");
            }
            if (input == null) {
                throw new InitError("Input stream was unexpectedly null.");
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
            process = cmd.createProcess();
            LOG.info("DCD process started");
        } catch (final ExecutionException e) {
            Notifications.Bus.notify(new Notification("DCDNotification", "DCD Error",
                "Unable to start a dcd server. Make sure that you have specified the path to the dcd-server and dcd-client executables correctly. You can specify executable paths under File > Settings > Languages & Frameworks > D Tools",
                NotificationType.ERROR), module.getProject());
            LOG.error("Error spawning DCD process", e);
//            throw new InitError(e.toString());
        }
        input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        output = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
    }

    // package privat for unit testing
    GeneralCommandLine buildDcdCommand(@NotNull final String path) {
        final GeneralCommandLine commandLine = new GeneralCommandLine(path);
        commandLine.setWorkDirectory(workingDirectory);
        commandLine.setRedirectErrorStream(true);
        final ParametersList parametersList = commandLine.getParametersList();

        if (isNotNullOrEmpty(flags)) {
            parametersList.addAll(flags);
        }

        // try to auto add project files in source root
        Arrays.stream(ProjectUtil.getRootManager(module).getSourceRoots(false))
            .forEach(root -> {
                parametersList.addParametersString("-I");
                parametersList.addParametersString(root.getCanonicalPath());
            });

        // try to auto add the compiler sources
        final List<String> compilerSources = getCompilerSourcePaths();
        for (final String s : compilerSources) {
            parametersList.addParametersString("-I");
            parametersList.addParametersString(s);
        }

        // try to auto add dub dependencies
        final DubConfigurationParser dubParser = new DubConfigurationParser(module.getProject(),
            ToolKey.DUB_KEY.getPath(), false);
        if (dubParser.canUseDub()) {
            dubParser.getDubProject().ifPresent(dubProject -> dubProject.getPackages().forEach(pkg -> {
                final List<String> sourcesDirs = pkg.getSourcesDirs();

                LOG.debug("adding source for ", pkg.getName());

                for(final String srcDir : sourcesDirs) {
                    parametersList.addParametersString("-I");
                    parametersList.addParametersString(String.format("%s%s", pkg.getPath(), srcDir));
                }
            }));
        } else {
            LOG.info("not possible to run 'dub describe'");
        }
        return commandLine;
    }

    private List<String> getCompilerSourcePaths() {
        final ArrayList<String> compilerSourcePaths = new ArrayList<>();
        final ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        final Sdk sdk = moduleRootManager.getSdk();

        if (sdk != null && (sdk.getSdkType() instanceof DlangSdkType)) {
            for (VirtualFile f: sdk.getRootProvider().getFiles(OrderRootType.SOURCES)) {
                if (f.exists() && f.isDirectory()) {
                    compilerSourcePaths.add(f.getPath());
                }
            }
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
        if (process != null) process.destroy();
        process = null;
        try {
            if (input != null) input.close();
        } catch (final IOException e) { /* Ignored */ }
        input = null;
        try {
            if (output != null) output.close();
        } catch (final IOException e) { /* Ignored */ }
        output = null;
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
            } /*catch (final DCDError e) {
                displayError(e.message);
            }*/ catch (final InterruptedException e) {
                LOG.error(e);
            }
        }
    }

    private void displayError(@NotNull final String message) {
        displayError(module.getProject(), message);
    }

    @Override
    public void moduleAdded() {
// No need to do anything here.
    }

    @Override
    public void initComponent() {
// No need to do anything here.
    }

    // Implemented methods for ModuleComponent.

    @Override
    public void disposeComponent() {
        executorService.shutdownNow();
        kill();
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "DCDCompletionServer";
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

    // Custom Exceptions
    public static abstract class DCDError extends Exception {
        // Using error to index errors since message might have extra information.
        final
        @NotNull
        String error;
        final
        @NotNull
        String message;
        final boolean killProcess;

        DCDError(@NotNull final String error, @NotNull final String message, final boolean killProcess) {
            this.error = error;
            this.message = message;
            this.killProcess = killProcess;
        }
    }

    public static class InitError extends DCDError {
        InitError(@NotNull final String error) {
            super(error, "Initializing dcd server failed with error: " + error, true);
        }
    }
}
