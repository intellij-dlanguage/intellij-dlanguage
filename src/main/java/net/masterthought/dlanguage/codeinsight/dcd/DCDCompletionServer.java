package net.masterthought.dlanguage.codeinsight.dcd;

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
import net.masterthought.dlanguage.DLanguageSdkType;
import net.masterthought.dlanguage.project.DubConfigurationParser;
import net.masterthought.dlanguage.project.DubPackage;
import net.masterthought.dlanguage.settings.SettingsChangeNotifier;
import net.masterthought.dlanguage.settings.ToolKey;
import net.masterthought.dlanguage.settings.ToolSettings;
import net.masterthought.dlanguage.utils.ExecUtil;
import net.masterthought.dlanguage.utils.NotificationUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static net.masterthought.dlanguage.utils.DUtil.isNotNullOrEmpty;

/**
 * Process wrapper for DCD Server.  Implements ModuleComponent so destruction of processes coincides with closing projects.
 */
public class DCDCompletionServer implements ModuleComponent, SettingsChangeNotifier {

    private static final Logger LOG = Logger.getInstance(DCDCompletionServer.class);

    public
    @NotNull
    final Module module;
    public
    @NotNull
    String workingDirectory;
    public
    @Nullable
    String path;
    public
    @NotNull
    String flags;
    private
    @Nullable
    Process process;
    private
    @Nullable
    BufferedReader input;
    private
    @Nullable
    BufferedWriter output;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Private constructor used during module component initialization.
     */
    public DCDCompletionServer(@NotNull Module module) {
        this.module = module;
        this.path = lookupPath();
        this.flags = lookupFlags();
        this.workingDirectory = lookupWorkingDirectory();
        // Ensure that we are notified of changes to the settings.
        module.getProject().getMessageBus().connect().subscribe(SettingsChangeNotifier.DCD_TOPIC, this);
    }

    private static void displayError(@NotNull Project project, @NotNull String message) {
        NotificationUtil.displayToolsNotification(NotificationType.ERROR, project, "dcd error", message);
    }

    @Nullable
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

    private void spawnProcess() throws DCDError {
        if(path == null || path.isEmpty()) {
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
        final DubConfigurationParser dubParser = new DubConfigurationParser(module.getProject(), ToolKey.DUB_KEY.getPath(module.getProject()));
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
        input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        output = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
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

        if (sdk != null && (sdk.getSdkType() instanceof DLanguageSdkType)) {
            final String path = sdk.getHomePath();
            if (isNotNullOrEmpty(path)) {
                if (SystemInfo.isMac) {
                    final String root = path.replaceAll("bin", "src");
                    compilerSources.add(root + "/phobos");
                    compilerSources.add(root + "/druntime/import");
                }
                // add linux and windows here once I know how
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
        return ToolKey.DCD_SERVER_KEY.getPath(module.getProject());
    }

    @NotNull
    private String lookupFlags() {
        return ToolKey.DCD_SERVER_KEY.getFlags(module.getProject());
    }

    /**
     * Kills the existing process and closes input and output if they exist.
     */
    private synchronized void kill() {
        if (process != null) process.destroy();
        process = null;
        try {
            if (input != null) input.close();
        } catch (IOException e) { /* Ignored */ }
        input = null;
        try {
            if (output != null) output.close();
        } catch (IOException e) { /* Ignored */ }
        output = null;
    }

    // Implemented methods for SettingsChangeNotifier
    @Override
    public void onSettingsChanged(@NotNull ToolSettings settings) {
        LOG.debug("DCD Server settings changed");
        kill();
        this.path = settings.getPath();
        this.flags = settings.getFlags();
        if(isNotNullOrEmpty(this.path)) {
            restart();
        }
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
            } catch (final DCDError e) {
                displayError(e.message);
            } catch (final InterruptedException e) {
                LOG.error(e);
            }
        }
    }

    private void displayError(@NotNull String message) {
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

        DCDError(@NotNull String error, @NotNull String message, boolean killProcess) {
            this.error = error;
            this.message = message;
            this.killProcess = killProcess;
        }
    }

    public static class InitError extends DCDError {
        InitError(@NotNull String error) {
            super(error, "Initializing dcd server failed with error: " + error, true);
        }
    }
}
