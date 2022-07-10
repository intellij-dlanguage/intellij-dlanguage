package io.github.intellij.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderImpl;
import com.intellij.execution.process.ColoredProcessHandler;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessListener;
import com.intellij.execution.process.ProcessOutputTypes;
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
import com.intellij.openapi.util.text.StringUtil;
import io.github.intellij.dlanguage.run.exception.NoSourcesException;
import io.github.intellij.dlanguage.DlangSdkType;
import io.github.intellij.dlanguage.run.exception.ModuleNotFoundException;
import io.github.intellij.dlanguage.run.exception.NoValidDlangSdkFound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;

public class DlangRunDmdState extends CommandLineState implements ProcessListener {

    private static final Logger LOG = Logger.getInstance(DlangRunDmdState.class);

    private final DlangRunDmdConfiguration config;

    DlangRunDmdState(@NotNull final ExecutionEnvironment environment, @NotNull final DlangRunDmdConfiguration config) {
        super(environment);
        this.config = config;
    }

    @NotNull
    @Override
    public ExecutionResult execute(@NotNull final Executor executor, @NotNull final ProgramRunner runner) throws ExecutionException {
        final TextConsoleBuilder consoleBuilder = new TextConsoleBuilderImpl(config.getProject());
        setConsoleBuilder(consoleBuilder);

        return super.execute(executor, runner);
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        try {
            final GeneralCommandLine cmd = getDmdCommandLine(config);
            final ProcessHandler handler = new ColoredProcessHandler(cmd.createProcess(), cmd.getCommandLineString());
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
     * <code>dmd -release -unittest -od{objFilesDir} -of{outputFilePath} sourceFile1.d sourceFile2.d</code>
     */
    private GeneralCommandLine getDmdCommandLine(final DlangRunDmdConfiguration config)
        throws ModuleNotFoundException, NoValidDlangSdkFound, NoSourcesException, ExecutionException {
        @Nullable final Module module = config.getConfigurationModule().getModule();
        if (module == null) {
            throw new ModuleNotFoundException();
        }

        if (module.isDisposed()) {
            LOG.warn("should not run dmd as module is disposed");
            throw new ModuleNotFoundException("module is disposed");
        }

        final ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        @Nullable final Sdk sdk = moduleRootManager.getSdk();

        if(sdk != null && DlangSdkType.class.isAssignableFrom(sdk.getSdkType().getClass())) {
            final DlangSdkType dlangSdkType = (DlangSdkType) sdk.getSdkType();

            final String dmdPath = dlangSdkType.getDmdPath(sdk);

            if (StringUtil.isEmptyOrSpaces(dmdPath)) {
                throw new ExecutionException("DMD executable is not specified");
            }
            if (!Paths.get(dmdPath).toFile().canExecute()) {
                throw new ExecutionException("DMD is not configured correctly");
            }

            final List<String> dmdParams = DlangDmdConfigToArgsConverter.getDmdParameters(config, module);

            final GeneralCommandLine cmd = new GeneralCommandLine()
                .withWorkDirectory(config.getProject().getBasePath())
                .withCharset(Charset.defaultCharset())
                .withExePath(dmdPath)
                .withParameters(dmdParams);

            LOG.debug(String.format("dmd command: %s", cmd.getCommandLineString()));
            return cmd;
        } else {
            LOG.warn("No valid D compiler found");
            throw new NoValidDlangSdkFound();
        }
    }

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
