package net.masterthought.dlanguage.unittest;

import com.intellij.execution.*;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.testframework.sm.SMTestRunnerConnectionUtil;
import com.intellij.execution.testframework.sm.runner.SMTRunnerConsoleProperties;
import com.intellij.execution.testframework.sm.runner.SMTestLocator;
import com.intellij.execution.testframework.sm.runner.ui.SMTRunnerConsoleView;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import net.masterthought.dlanguage.DLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DUnitTestRunProfileState implements RunProfileState {

    private final ExecutionEnvironment environment;

    public DUnitTestRunProfileState(final ExecutionEnvironment environment)
    {
        this.environment = environment;
    }

    @Nullable
    @Override
    public ExecutionResult execute(final Executor executor, @NotNull final ProgramRunner programRunner) throws ExecutionException {
        final Project project = environment.getProject();
        if (project == null) {
//            handleError("No project found.");
            return null;
        }

        final RunnerAndConfigurationSettings settings = environment.getRunnerAndConfigurationSettings();

        if (settings == null) {
//            handleError(project, "No runner and configuration settings found.");
            return null;
        }

        final RunConfiguration configuration = settings.getConfiguration();
        if (!(configuration instanceof DUnitTestRunConfiguration)) {
//            handleError(project, "The supplied configuration is not a " + DUnitTestRunConfiguration.class.getSimpleName() + ".");
            return null;
        }

        final DUnitTestRunConfiguration unitTestRunConfiguration = (DUnitTestRunConfiguration) configuration;

        // Create the test runner
        final DUnitTestRunProcessHandler processHandler = new DUnitTestRunProcessHandler(project, unitTestRunConfiguration);

        final SMTRunnerConsoleProperties properties = new SMTRunnerConsoleProperties(configuration, DLanguage.INSTANCE.getDisplayName(), executor) {
            @Override
            public SMTestLocator getTestLocator() {
                return DUnitTestLocationProvider.getInstance();
            }
        };

//        properties.addStackTraceFilter(new ApexStackTraceFilter());

        final SMTRunnerConsoleView consoleView = (SMTRunnerConsoleView) SMTestRunnerConnectionUtil
            .createAndAttachConsole(DLanguage.INSTANCE.getDisplayName(), processHandler, properties);

        Disposer.register(project, consoleView);

        // Integrated code coverage
        // NOTE: Only necessary if you’re going to add code coverage support, and that’s a TOTALLY different discussion!
//        CoverageHelper.attachToProcess(unitTestRunConfiguration, processHandler, getRunnerSettings());

        // Register an action to re-run failed tests
        final DUnitTestRerunFailedTestsAction rerunFailedTestsAction = new DUnitTestRerunFailedTestsAction(project, consoleView);
        rerunFailedTestsAction.init(consoleView.getProperties());
        rerunFailedTestsAction.setModelProvider(() -> consoleView.getResultsViewer());

        final DefaultExecutionResult executionResult = new DefaultExecutionResult(consoleView, processHandler);
        executionResult.setRestartActions(rerunFailedTestsAction);

        // Start the process handler
        ApplicationManager
            .getApplication()
            .invokeLater(() -> processHandler.startProcessing());

        return executionResult;
    }

//    @Override
//    public RunnerSettings getRunnerSettings()
//    {
//        return environment.getRunnerSettings();
//    }
//
//    @Override
//    public ConfigurationPerRunnerSettings getConfigurationSettings()
//    {
//        return environment.getConfigurationSettings();
//    }
}
