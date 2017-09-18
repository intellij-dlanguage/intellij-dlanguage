package net.masterthought.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderImpl;
import com.intellij.execution.process.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.io.FileUtil;
import net.masterthought.dlanguage.DlangSdkType;
import net.masterthought.dlanguage.run.exception.ModuleNotFoundException;
import net.masterthought.dlanguage.run.exception.NoSourcesException;
import net.masterthought.dlanguage.run.exception.NoValidDlangSdkFound;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class DlangRunDmdState extends CommandLineState implements ProcessListener {

    private static final Logger LOG = Logger.getInstance(DlangRunDmdState.class);

    private final DlangRunDmdConfiguration config;
    private Executor executor;

    protected DlangRunDmdState(@NotNull final ExecutionEnvironment environment, @NotNull final DlangRunDmdConfiguration config) {
        super(environment);
        this.config = config;
    }

    @NotNull
    @Override
    public ExecutionResult execute(@NotNull final Executor executor, @NotNull final ProgramRunner runner) throws ExecutionException {
        final TextConsoleBuilder consoleBuilder = new TextConsoleBuilderImpl(config.getProject());
        setConsoleBuilder(consoleBuilder);
        this.executor = executor;
        return super.execute(executor, runner);
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        try {
            final GeneralCommandLine dmdCommandLine = getDmdCommandLine(config);
            final OSProcessHandler handler = new OSProcessHandler(dmdCommandLine.createProcess(), dmdCommandLine.getCommandLineString());
            handler.addProcessListener(this);
            return handler;
        } catch (final NoValidDlangSdkFound e) {
            throw new ExecutionException("No valid DMD SDK found!");
        } catch (final NoSourcesException e) {
            throw new ExecutionException("No D Language source files found in directory: " + e.getSourcesRoot());
        } catch (final ModuleNotFoundException e) {
            throw new ExecutionException("Run configuration has no module selected.");
        } catch (final ExecutionException e) {
            final String message = e.getMessage();
            final boolean isEmpty = message.equals("Executable is not specified");
            final boolean notCorrect = message.startsWith("Cannot run program");
            if (isEmpty || notCorrect) {
                Notifications.Bus.notify(
                    new Notification("DMD run configuration", "DMD settings",
                        "DMD executable path is " + (isEmpty ? "empty" : "not specified correctly") +
                            "<br/><a href='configure'>Configure</a>",
                        NotificationType.ERROR), config.getProject());
            }
            throw e;
        }
    }

    /**
     * Build command line:
     * <code>
     * dmd @compilerArguments.txt
     * </code>
     * "compilerArguments.txt" is temporary file with DMD compiler arguments separated with '\n'. E.g.
     * <code>
     * -release
     * -unittest
     * -od{objFilesDir}
     * -of{outputFilePath}
     * sourceFile1.d
     * sourceFile2.d
     * </code>
     */
    private GeneralCommandLine getDmdCommandLine(final DlangRunDmdConfiguration config)
        throws ModuleNotFoundException, NoValidDlangSdkFound, NoSourcesException, ExecutionException {
        final Module module = config.getConfigurationModule().getModule();
        if (module == null) {
            throw new ModuleNotFoundException();
        }

        final ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        final Sdk sdk = moduleRootManager.getSdk();

        if(sdk != null && DlangSdkType.class.isAssignableFrom(sdk.getSdkType().getClass())) {
            final DlangSdkType dlangSdkType = DlangSdkType.class.cast(sdk.getSdkType());
            final List<String> dmdParameters = DlangDmdConfigToArgsConverter.getDmdParameters(config, module);

            final GeneralCommandLine cmd = new GeneralCommandLine();
            cmd.withWorkDirectory(config.getProject().getBasePath());
            cmd.setExePath(dlangSdkType.getDmdPath(sdk));
            cmd.addParameter(tempFileWithParameters(dmdParameters));
            LOG.debug(String.format("dmd command: %s", cmd.getCommandLineString()));
            return cmd;
        } else {
            LOG.error("No valid D compiler found");
            throw new NoValidDlangSdkFound();
        }
    }

    //Create file with the list of all dmdParameters
    private String tempFileWithParameters(final List<String> dmdParameters) throws ExecutionException {
        final String sep = System.lineSeparator();
        final File tmpFile;
        try {
            tmpFile = FileUtil.createTempFile("dmd", "src");
            final OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(tmpFile), "UTF-8");
            for (final String srcFilePath : dmdParameters) {
                output.write(srcFilePath);
                output.write(sep);
            }
            output.close();
        } catch (final IOException exc) {
            throw new ExecutionException("Can't create temporary file with arguments", exc);
        }

        return "@" + tmpFile.getAbsolutePath();

    }

    /* Implementations of ProcessListener interface methods */
    @Override
    public void startNotified(final ProcessEvent event) {
        //skip
    }

    @Override
    public void processTerminated(final ProcessEvent event) {
        if (event.getExitCode() == 0) {
            event.getProcessHandler().notifyTextAvailable("* Compilation successful", ProcessOutputTypes.SYSTEM);
        } else {
            event.getProcessHandler().notifyTextAvailable("* Compilation finished with errors", ProcessOutputTypes.SYSTEM);
        }
    }

    @Override
    public void processWillTerminate(final ProcessEvent event, final boolean willBeDestroyed) {
        LOG.debug("Run DMD process terminating");
    }

    @Override
    public void onTextAvailable(final ProcessEvent event, final Key outputType) {
        //skip
    }

}
