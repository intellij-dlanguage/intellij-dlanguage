package net.masterthought.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.CommandLineTokenizer;
import com.intellij.execution.configurations.GeneralCommandLine;
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

import java.util.LinkedList;
import java.util.List;


public class DLanguageRunDubState extends CommandLineState {
    private DLanguageRunDubConfiguration config;
    private Executor executor;

    protected DLanguageRunDubState(@NotNull ExecutionEnvironment environment, @NotNull DLanguageRunDubConfiguration
            config) {
        super(environment);
        this.config = config;
    }

    @NotNull
    @Override
    public ExecutionResult execute(@NotNull Executor executor, @NotNull ProgramRunner runner) throws ExecutionException {
        TextConsoleBuilder consoleBuilder = new TextConsoleBuilderImpl(config.getProject());
        setConsoleBuilder(consoleBuilder);
        this.executor = executor;
        return super.execute(executor, runner);
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        try {
            GeneralCommandLine dubCommandLine = getExecutableCommandLine(config);
            return new OSProcessHandler(dubCommandLine.createProcess(), dubCommandLine.getCommandLineString());
        } catch (ExecutionException e) {
            String message = e.getMessage();
            final Project project = config.getProject();

            boolean isEmpty = message.equals("DUB executable is not specified");
            boolean notCorrect = message.startsWith("Cannot run program");
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
    private GeneralCommandLine getExecutableCommandLine(DLanguageRunDubConfiguration config)
            throws ExecutionException {
        Module module = config.getConfigurationModule().getModule();
        if (module == null) {
            throw new ExecutionException("Run configuration has no module selected.");
        }

        String dubPath = ToolKey.DUB_KEY.getPath(config.getProject());
        if (StringUtil.isEmptyOrSpaces(dubPath)) {
            throw new ExecutionException("DUB executable is not specified");
        }

        VirtualFile sourcesRoot = getSourceRoot(module);
        GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setExePath(dubPath);

        if (!StringUtil.isEmptyOrSpaces(config.getWorkingDir())) {
            commandLine.withWorkDirectory(config.getWorkingDir());
        } else {
            commandLine.withWorkDirectory(config.getProject().getBasePath());
        }

        // Add command line parameters

        boolean toBuild = config.getGeneralDubOptions() == 0;
        boolean toRun = config.getGeneralDubOptions() == 1;
        boolean toTest = config.getGeneralDubOptions() == 2;


//        cbTempBuild.setEnabled(inRunState);
//               cbCoverage.setEnabled(inTestState);
//               tfMainFile.setEnabled(inTestState);
//               cbRdmd.setEnabled(inBuildState || inRunState);
//               cbParallel.setEnabled(inBuildState || inRunState);

        if (toBuild) {
            commandLine.addParameter("build");
        } else if (toTest) {
            commandLine.addParameter("test");
        } else if (toRun) {
            commandLine.addParameter("run");
        }

        if (toRun) {
            if (config.isCbTempBuild()) {
                commandLine.addParameter("--temp-build");
            }
        }

        if (toTest) {
            if (config.isCbCoverage()) {
                commandLine.addParameter("--coverage");
            }
            if (!config.getTfMainFile().isEmpty()) {
                commandLine.addParameter("--main-file");
                commandLine.addParameter(config.getTfMainFile());
            }
        }

        if (config.isCbRdmd()) {
            commandLine.addParameter("--rdmd");
        }
        if (config.isCbForce()) {
            commandLine.addParameter("--force");
        }
        if (config.isCbNoDeps()) {
            commandLine.addParameter("--nodeps");
        }
        if (config.isCbForceRemove()) {
            commandLine.addParameter("--force-remove");
        }
        if (config.isCbCombined()) {
            commandLine.addParameter("--combined");
        }
        if (config.isCbParallel()) {
            commandLine.addParameter("--parallel");
        }
        if (config.isQuiet()) {
            commandLine.addParameter("-q");
        }
        if (config.isVerbose()) {
            commandLine.addParameter("-v");
        }
        if (!config.getTfArch().isEmpty()) {
            commandLine.addParameter("--arch");
            commandLine.addParameter(config.getTfArch());
        }
        if (!config.getTfBuild().isEmpty()) {
            commandLine.addParameter("--build");
            commandLine.addParameter(config.getTfBuild());
        }
        if (!config.getTfConfig().isEmpty()) {
            commandLine.addParameter("--config");
            commandLine.addParameter(config.getTfConfig());
        }
        if (!config.getTfDebug().isEmpty()) {
            commandLine.addParameter("--debug");
            commandLine.addParameter(config.getTfDebug());
        }
        if (!config.getTfCompiler().isEmpty()) {
            commandLine.addParameter("--compiler");
            commandLine.addParameter(config.getTfCompiler());
        }

        boolean bmSeparate = config.getBuildMode() == 0;
        boolean bmAll = config.getBuildMode() == 1;
        boolean bmSingle = config.getBuildMode() == 2;

        if (bmSeparate) {
            commandLine.addParameter("--build-mode");
            commandLine.addParameter("separate");
        }
        if (bmAll) {
            commandLine.addParameter("--build-mode");
            commandLine.addParameter("allAtOnce");
        }
        if (bmSingle) {
            commandLine.addParameter("--build-mode");
            commandLine.addParameter("singleFile");
        }

        if (StringUtil.isEmptyOrSpaces(config.getAdditionalParams())) {
            commandLine.addParameters(splitArguments(config.getAdditionalParams()));
        }

        return commandLine;
    }

    private String[] splitArguments(String arguments) {
        if (StringUtil.isEmptyOrSpaces(arguments)) {
            return new String[0];
        }

        List<String> argsLst = new LinkedList<String>();
        CommandLineTokenizer tokenizer = new CommandLineTokenizer(arguments);
        while (tokenizer.hasMoreTokens()) {
            argsLst.add(tokenizer.nextToken());
        }
        if (argsLst.size() > 0) {
            return (String[]) argsLst.toArray();
        } else {
            return new String[0];
        }
    }

    private VirtualFile getSourceRoot(Module module) {
        if (module != null) {
            VirtualFile[] sourcesRoots = ModuleRootManager.getInstance(module).getSourceRoots();
            if (sourcesRoots.length >= 1) {
                return sourcesRoots[0];
            }
        }
        return null;
    }
}
