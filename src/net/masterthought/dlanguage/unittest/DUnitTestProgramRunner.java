package net.masterthought.dlanguage.unittest;

import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.BasicProgramRunner;
import org.jetbrains.annotations.NotNull;

public class DUnitTestProgramRunner extends BasicProgramRunner {
    @NotNull
    @Override
    public String getRunnerId() {
        return DUnitTestProgramRunner.class.getSimpleName();
    }

    @Override
    public boolean canRun(@NotNull String executorId, @NotNull RunProfile profile) {
        return (DefaultRunExecutor.EXECUTOR_ID.equals(executorId))
                && (profile instanceof DUnitTestRunConfiguration);
    }
}
