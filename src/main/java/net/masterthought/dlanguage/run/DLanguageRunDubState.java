package net.masterthought.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderImpl;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import net.masterthought.dlanguage.settings.ToolKey;
import net.masterthought.dlanguage.utils.DToolsNotificationListener;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static net.masterthought.dlanguage.utils.DUtil.isNotNullOrEmpty;


public class DLanguageRunDubState extends CommandLineState {
    private final DLanguageRunDubConfiguration config;
    private Executor executor;

    DLanguageRunDubState(@NotNull final ExecutionEnvironment environment,
                         @NotNull final DLanguageRunDubConfiguration config) {
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
            final GeneralCommandLine dubCommandLine = getExecutableCommandLine(config);
            return new OSProcessHandler(dubCommandLine.createProcess(), dubCommandLine.getCommandLineString());
        } catch (final ExecutionException e) {
            final String message = e.getMessage();
            final Project project = config.getProject();

            final boolean isEmpty = message.equals("DUB executable is not specified");
            final boolean notCorrect = message.startsWith("Cannot run program");
            if (isEmpty || notCorrect) {
                Notifications.Bus.notify(
                    new Notification("DUB run configuration", "DUB settings",
                        "DUB executable is " + (isEmpty ? "not specified" : "not specified correctly") +
                            "<br/><a href='configureDLanguageTools'>Configure</a> executable",
                        NotificationType.ERROR, new DToolsNotificationListener(project)), project);
            }
            throw e;
        }
    }

    /* Build command line to start DUB executable
     */
    private GeneralCommandLine getExecutableCommandLine(final DLanguageRunDubConfiguration config)
        throws ExecutionException {
        if (executor.getActionName().equals(DefaultDebugExecutor.EXECUTOR_ID)) {
            //todo add custom option overrides
        }
        final Module module = config.getConfigurationModule().getModule();
        if (module == null) {
            throw new ExecutionException("Run configuration has no module selected.");
        }

        final String dubPath = ToolKey.DUB_KEY.getPath(config.getProject());
        if (StringUtil.isEmptyOrSpaces(dubPath)) {
            throw new ExecutionException("DUB executable is not specified");
        }

        final VirtualFile sourcesRoot = getSourceRoot(module);
        final GeneralCommandLine cmd = new GeneralCommandLine();
        cmd.setExePath(dubPath);

        if (StringUtil.isNotEmpty(config.getWorkingDir())) {
            cmd.withWorkDirectory(config.getWorkingDir());
        } else {
            cmd.withWorkDirectory(config.getProject().getBasePath());
        }

        // Add command line parameters

        final boolean toBuild = config.getGeneralDubOptions() == 0;
        final boolean toRun = config.getGeneralDubOptions() == 1;
        final boolean toTest = config.getGeneralDubOptions() == 2;

        if (toBuild) {
            cmd.addParameter("build");
        } else if (toTest) {
            cmd.addParameter("test");
        } else if (toRun) {
            cmd.addParameter("run");
        }

        if (toRun) {
            if (config.isCbTempBuild()) {
                cmd.addParameter("--temp-build");
            }
        }

        if (toTest) {
            if (config.isCbCoverage()) {
                cmd.addParameter("--coverage");
            }
            if (config.getTfMainFile() != null) {
                cmd.addParameter("--main-file");
                cmd.addParameter(config.getTfMainFile());
            }
        }

        if (config.isCbRdmd()) {
            cmd.addParameter("--rdmd");
        }
        if (config.isCbForce()) {
            cmd.addParameter("--force");
        }
        if (config.isCbNoDeps()) {
            cmd.addParameter("--nodeps");
        }
        if (config.isCbForceRemove()) {
            cmd.addParameter("--force-remove");
        }
        if (config.isCbCombined()) {
            cmd.addParameter("--combined");
        }
        if (config.isCbParallel()) {
            cmd.addParameter("--parallel");
        }
        if (config.isQuiet()) {
            cmd.addParameter("-q");
        }
        if (config.isVerbose()) {
            cmd.addParameter("-v");
        }

        if (isNotNullOrEmpty(config.getTfArch())) {
            cmd.addParameter("--arch");
            cmd.addParameter(config.getTfArch());
        }
        if (isNotNullOrEmpty(config.getTfBuild())) {
            cmd.addParameter("--build");
            cmd.addParameter(config.getTfBuild());
        }
        if (isNotNullOrEmpty(config.getTfConfig())) {
            cmd.addParameter("--config");
            cmd.addParameter(config.getTfConfig());
        }
        if (isNotNullOrEmpty(config.getTfDebug())) {
            cmd.addParameter("--debug");
            cmd.addParameter(config.getTfDebug());
        }
        if (isNotNullOrEmpty(config.getTfCompiler())) {
            cmd.addParameter("--compiler");
            cmd.addParameter(config.getTfCompiler());
        }

        final boolean bmSeparate = config.getBuildMode() == 0;
        final boolean bmAll = config.getBuildMode() == 1;
        final boolean bmSingle = config.getBuildMode() == 2;

        if (bmSeparate) {
            cmd.addParameter("--build-mode");
            cmd.addParameter("separate");
        }
        if (bmAll) {
            cmd.addParameter("--build-mode");
            cmd.addParameter("allAtOnce");
        }
        if (bmSingle) {
            cmd.addParameter("--build-mode");
            cmd.addParameter("singleFile");
        }

        if (isNotNullOrEmpty(config.getAdditionalParams())) {
            cmd.addParameters(Arrays.asList(config.getAdditionalParams().split("\\s")));
        }

        return cmd;

    }

    private VirtualFile getSourceRoot(final Module module) {
        if (module != null) {
            final VirtualFile[] sourcesRoots = ModuleRootManager.getInstance(module).getSourceRoots();
            if (sourcesRoots.length >= 1) {
                return sourcesRoots[0];
            }
        }
        return null;
    }
}
