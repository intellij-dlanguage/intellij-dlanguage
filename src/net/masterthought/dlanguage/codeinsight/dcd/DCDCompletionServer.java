package net.masterthought.dlanguage.codeinsight.dcd;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleComponent;
import net.masterthought.dlanguage.utils.ExecUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Process wrapper for DCD Server.  Implements ModuleComponent so destruction of processes coincides with closing projects.
 */
public class DCDCompletionServer implements ModuleComponent {

    private String DCD_PATH = "/home/kings/development/projects/dlang/DCD/bin/dcd-server";

    public @NotNull final Module module;
    public @NotNull String workingDirectory;
    public @Nullable String path = DCD_PATH;
    private @Nullable Process process;
    private @Nullable BufferedReader input;
    private @Nullable BufferedWriter output;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private boolean enabled = true;


    @Nullable
    public synchronized void exec() throws DCDError {
        if (process == null) { spawnProcess(); }
        if (output == null) { throw new InitError("Output stream was unexpectedly null."); }
        if (input == null) { throw new InitError("Input stream was unexpectedly null."); }
    }

    private void spawnProcess() throws DCDError {
        GeneralCommandLine commandLine = new GeneralCommandLine(path);
        commandLine.setWorkDirectory(workingDirectory);
        commandLine.setRedirectErrorStream(true);
        try {
            process = commandLine.createProcess();
        } catch (ExecutionException e) {
            throw new InitError(e.toString());
        }
        input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        output = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
    }

    @NotNull
    private String lookupWorkingDirectory() {
        return ExecUtil.guessWorkDir(module);
    }

    /**
     * Private constructor used during module component initialization.
     */
    public DCDCompletionServer(@NotNull Module module) {
        this.module = module;
//        this.path = lookupPath();
//        this.flags = lookupFlags();
        this.workingDirectory = lookupWorkingDirectory();
        // Ensure that we are notified of changes to the settings.
//        module.getProject().getMessageBus().connect().subscribe(SettingsChangeNotifier.GHC_MODI_TOPIC, this);
    }


    // Custom Exceptions
    public static abstract class DCDError extends Exception {
        // Using error to index errors since message might have extra information.
        final @NotNull String error;
        final @NotNull String message;
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

    /**
     * Kills the existing process and closes input and output if they exist.
     */
    private synchronized void kill() {
        if (process != null) process.destroy();
        process = null;
        try { if (input != null) input.close(); } catch (IOException e) { /* Ignored */ }
        input = null;
        try { if (output != null) output.close(); } catch (IOException e) { /* Ignored */ }
        output = null;
    }


    // Implemented methods for ModuleComponent.

    @Override
    public void projectOpened() {
        // No need to do anything here.
    }

    @Override
    public void projectClosed() {
// No need to do anything here.
    }

    @Override
    public void moduleAdded() {
// No need to do anything here.
    }

    @Override
    public void initComponent() {
// No need to do anything here.
    }

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
}
