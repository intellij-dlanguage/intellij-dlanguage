package net.masterthought.dlanguage.unittest;

import com.intellij.execution.*;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.testframework.TestFrameworkRunningModel;
import com.intellij.execution.testframework.sm.SMTestRunnerConnectionUtil;
import com.intellij.execution.testframework.sm.runner.SMTRunnerConsoleProperties;
import com.intellij.execution.testframework.sm.runner.ui.SMTRunnerConsoleView;
import com.intellij.execution.testframework.ui.BaseTestsOutputConsoleView;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Getter;
import net.masterthought.dlanguage.DLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DUnitTestRunProfileState implements RunProfileState
{
    private final ExecutionEnvironment environment;

    public DUnitTestRunProfileState(ExecutionEnvironment environment)
    {
        this.environment = environment;
    }

    @Nullable
    @Override
    public ExecutionResult execute(Executor executor, @NotNull ProgramRunner programRunner) throws ExecutionException
    {
        Project project = environment.getProject();
        if (project == null)
        {
//            handleError("No project found.");
            return null;
        }

        RunnerAndConfigurationSettings settings = environment.getRunnerAndConfigurationSettings();
        if (settings == null)
        {
//            handleError(project, "No runner and configuration settings found.");
            return null;
        }

        final RunConfiguration configuration = settings.getConfiguration();
        if (!(configuration instanceof DUnitTestRunConfiguration))
        {
//            handleError(project, "The supplied configuration is not a " + DUnitTestRunConfiguration.class.getSimpleName() + ".");
            return null;
        }
        DUnitTestRunConfiguration unitTestRunConfiguration = (DUnitTestRunConfiguration) configuration;

        // Create the test runner
        final DUnitTestRunProcessHandler processHandler = new DUnitTestRunProcessHandler(project, unitTestRunConfiguration);
        SMTRunnerConsoleProperties consoleProperties = new SMTRunnerConsoleProperties(configuration, DLanguage.INSTANCE.getDisplayName(), executor);
//        consoleProperties.addStackTraceFilter(new ApexStackTraceFilter());
        final SMTRunnerConsoleView consoleView = (SMTRunnerConsoleView) SMTestRunnerConnectionUtil.createConsoleWithCustomLocator(
                DLanguage.INSTANCE.getDisplayName(),
                consoleProperties,
                environment,
                new DUnitTestLocationProvider(),
                true,
                null);
        consoleView.attachToProcess(processHandler);
        Disposer.register(project, consoleView);

        // Integrated code coverage
        // NOTE: Only necessary if you’re going to add code coverage support, and that’s a TOTALLY different discussion!
//        CoverageHelper.attachToProcess(unitTestRunConfiguration, processHandler, getRunnerSettings());

        // Register an action to re-run failed tests
        DUnitTestRerunFailedTestsAction rerunFailedTestsAction = new DUnitTestRerunFailedTestsAction(project, consoleView);
        rerunFailedTestsAction.init(((BaseTestsOutputConsoleView) consoleView).getProperties());
        rerunFailedTestsAction.setModelProvider(new Getter<TestFrameworkRunningModel>()
        {
            @Override
            public TestFrameworkRunningModel get()
            {
                return consoleView.getResultsViewer();
            }
        });

        DefaultExecutionResult executionResult = new DefaultExecutionResult(consoleView, processHandler);
        executionResult.setRestartActions(rerunFailedTestsAction);

        // Start the process handler
        ApplicationManager.getApplication().invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                processHandler.startProcessing();
            }
        });

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