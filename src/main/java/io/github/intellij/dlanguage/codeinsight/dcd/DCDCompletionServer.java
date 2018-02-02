package io.github.intellij.dlanguage.codeinsight.dcd;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.containers.ContainerUtil;
import io.github.intellij.dlanguage.project.DubPackage;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.settings.ToolSettings;
import io.github.intellij.dlanguage.DlangSdkType;
import io.github.intellij.dlanguage.project.DubConfigurationParser;
import io.github.intellij.dlanguage.settings.SettingsChangeNotifier;
import io.github.intellij.dlanguage.utils.ExecUtil;
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
public class DCDCompletionServer implements ModuleComponent, SettingsChangeNotifier {

    private static final Logger LOG = Logger.getInstance(DCDCompletionServer.class);

    @NotNull
    public final Module module;

    @NotNull
    private final String workingDirectory;

    @Nullable
    public String path;

    @NotNull
    public String flags;

    public Process process;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Private constructor used during module component initialization.
     */
    public DCDCompletionServer(@NotNull final Module module) {
        this.module = module;
        this.path = lookupPath();
        this.flags = lookupFlags();
        this.workingDirectory = lookupWorkingDirectory();
        // Ensure that we are notified of changes to the settings.
        module.getProject().getMessageBus().connect().subscribe(SettingsChangeNotifier.DCD_TOPIC, this);
        try {
            // kill in case there was a dead process
            //kill();
            spawnProcess();
        } catch (DCDError dcdError) {
            dcdError.printStackTrace();
        }
    }

    private static void displayError(@NotNull final Project project, @NotNull final String message) {
        NotificationUtil.displayToolsNotification(NotificationType.ERROR, project, "dcd error", message);
    }

    synchronized void exec() throws DCDError {
        if(process == null || !process.isAlive())
            spawnProcess();
    }

    private void spawnProcess() throws DCDError {
        if (path == null || path.isEmpty()) {
            LOG.warn("request made to spawn process for DCD Server but path is not set");
            return;
        }
        final GeneralCommandLine commandLine = new GeneralCommandLine(path);
        commandLine.setWorkDirectory(workingDirectory);
        commandLine.setRedirectErrorStream(true);
        final ParametersList parametersList = commandLine.getParametersList();

        if (isNotNullOrEmpty(flags)) {
            final List<String> importList = Arrays.asList(flags.split(","));
            for (final String item : importList) {
                System.out.println("IMPORT: " + item);
                parametersList.addParametersString("-I");
                parametersList.addParametersString(item);
            }
        }

        // try to auto add project files in source root
        final String sources = getRootSourceDir();
        if (isNotNullOrEmpty(sources)) {
            parametersList.addParametersString("-I");
            parametersList.addParametersString(sources);
        }

        // try to auto add the compiler sources
        final List<String> compilerSources = getCompilerSourceDirs();
        for (final String s : compilerSources) {
            parametersList.addParametersString("-I");
            parametersList.addParametersString(s);
        }

        // try to auto add dub dependencies
        final DubConfigurationParser dubParser = new DubConfigurationParser(module.getProject(),
            ToolKey.DUB_KEY.getPath(), false);
        if (dubParser.canUseDub()) {
            final List<DubPackage> dependencies = dubParser.getDubPackageDependencies();
            for (final DubPackage pkg : dependencies) {
                parametersList.addParametersString("-I");
                final String pkgSource = String.format("%s%s", pkg.getPath(), pkg.getSourcesDir());
                LOG.debug("adding source for ", pkg.getName());
                parametersList.addParametersString(pkgSource);
            }
        } else {
            LOG.info("not possible to run 'dub describe'");
        }

        try {
            process = commandLine.createProcess();
            LOG.info("DCD process started");
        } catch (final ExecutionException e) {
            LOG.error("Error spawning DCD process", e);
            throw new InitError(e.toString());
        }
    }

    private String getRootSourceDir() {
        final Project myProject = module.getProject();
        final List<VirtualFile> sourceRoots = new ArrayList<>();
        final ProjectRootManager projectRootManager = ProjectRootManager.getInstance(myProject);
        ContainerUtil.addAll(sourceRoots, projectRootManager.getContentSourceRoots());
        return sourceRoots.isEmpty() ? null : sourceRoots.get(0).getPath();
    }

    private List<String> getCompilerSourceDirs() {
        final ArrayList<String> compilerSources = new ArrayList<>();
        final ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        final Sdk sdk = moduleRootManager.getSdk();

        if (sdk != null && (sdk.getSdkType() instanceof DlangSdkType)) {
            final String path = sdk.getHomePath();

            if (isNotNullOrEmpty(path)) {
                final String root = path.replace("/bin", "") + "/src";
                compilerSources.add(root + "/phobos");
                compilerSources.add(root + "/druntime/import");
            }
        }
        return compilerSources;
    }

    @NotNull
    private String lookupWorkingDirectory() {
        return ExecUtil.guessWorkDir(module);
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
     * Kills the existing process.
     */
    private synchronized void kill() {
        final DCDCompletionClient client = new DCDCompletionClient();
        client.shutdownServer();

        if (process != null) {
            if (process.isAlive())
                process.destroy();
            process = null;
        }
    }

    // Implemented methods for SettingsChangeNotifier
    @Override
    public void onSettingsChanged(@NotNull final ToolSettings settings) {
        LOG.debug("DCD Server settings changed");
        kill();
        this.path = settings.getPath();
        this.flags = settings.getFlags();
        if (isNotNullOrEmpty(this.path)) {
            restart();
        }
    }

    /**
     * Restarts the dcd-server.
     */
    public synchronized void restart() {
        kill();
        if (isNotNullOrEmpty(path)) {
            try {
                spawnProcess();
            } catch (final DCDError e) {
                displayError(e.message);
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
