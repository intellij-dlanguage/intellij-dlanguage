package io.github.intellij.dlanguage.unittest;

import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.DefaultProgramRunner;
import org.jetbrains.annotations.NotNull;

public class DUnitTestProgramRunner extends DefaultProgramRunner {

    @NotNull
    @Override
    public String getRunnerId() {
        return DUnitTestProgramRunner.class.getSimpleName();
    }

    @Override
    public boolean canRun(@NotNull final String executorId, @NotNull final RunProfile profile) {
        return (DefaultRunExecutor.EXECUTOR_ID.equals(executorId))
            && (profile instanceof DUnitTestRunConfiguration);
    }
}
