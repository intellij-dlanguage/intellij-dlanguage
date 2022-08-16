package io.github.intellij.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ColoredProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.utils.DToolsNotificationAction;
import io.github.intellij.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Paths;
import java.util.Arrays;


public class DlangRunDubState extends CommandLineState {
    private final DlangRunDubConfiguration config;

    DlangRunDubState(@NotNull final ExecutionEnvironment environment,
                     @NotNull final DlangRunDubConfiguration config) {
        super(environment);
        this.config = config;
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        try {
            final GeneralCommandLine dubCommandLine = getExecutableCommandLine(config);
            final ProcessHandler handler = new ColoredProcessHandler(dubCommandLine.createProcess(), dubCommandLine.getCommandLineString());
            ProcessTerminatedListener.attach(handler, config.getProject());
            return handler;
        } catch (final ExecutionException e) {
            final String message = e.getMessage();
            final Project project = config.getProject();

            final boolean isEmpty = message.equals("DUB executable is not specified");
            final boolean notCorrect = message.startsWith("Cannot run program");
            if (isEmpty || notCorrect) {
                Notifications.Bus.notify(
                    new Notification("DUB run configuration", "DUB settings",
                        "DUB executable is " + (isEmpty ? "not specified" : "not specified correctly"),
                        NotificationType.ERROR)
                        .addAction(new DToolsNotificationAction("Configure")),
                    project);
            }
            throw e;
        }
    }

    /* Build command line to start DUB executable
     */
    private GeneralCommandLine getExecutableCommandLine(final DlangRunDubConfiguration config) throws ExecutionException {
        final Module module = config.getConfigurationModule().getModule();
        if (module == null) {
            throw new ExecutionException("Run configuration has no module selected.");
        }

        final String dubPath = ToolKey.DUB_KEY.getPath();
        if (StringUtil.isEmptyOrSpaces(dubPath)) {
            throw new ExecutionException("DUB executable is not specified");
        }
        if (!Paths.get(dubPath).toFile().canExecute()) {
            throw new ExecutionException("DUB is not configured correctly");
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
            if (StringUtil.isNotEmpty(config.getTfMainFile())) {
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

        if (DUtil.isNotNullOrEmpty(config.getTfArch())) {
            cmd.addParameter("--arch");
            cmd.addParameter(config.getTfArch());
        }
        if (DUtil.isNotNullOrEmpty(config.getTfBuild())) {
            cmd.addParameter("--build");
            cmd.addParameter(config.getTfBuild());
        }
        if (DUtil.isNotNullOrEmpty(config.getTfConfig())) {
            cmd.addParameter("--config");
            cmd.addParameter(config.getTfConfig());
        }
        if (DUtil.isNotNullOrEmpty(config.getTfDebug())) {
            cmd.addParameter("--debug");
            cmd.addParameter(config.getTfDebug());
        }
        if (DUtil.isNotNullOrEmpty(config.getTfCompiler())) {
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

        if (DUtil.isNotNullOrEmpty(config.getAdditionalParams())) {
            cmd.addParameters(Arrays.asList(config.getAdditionalParams().split("\\s")));
        }

        cmd.withEnvironment(config.getEnvVars());

        return cmd;

    }

    @Nullable
    private VirtualFile getSourceRoot(@Nullable final Module module) {
        if (module != null && !module.isDisposed()) {
            final VirtualFile[] sourcesRoots = ModuleRootManager.getInstance(module).getSourceRoots();
            if (sourcesRoots.length >= 1) {
                return sourcesRoots[0];
            }
        }
        return null;
    }
}
