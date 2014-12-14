package net.masterthought.dlanguage.run;

import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.DefaultProgramRunner;
import org.jetbrains.annotations.NotNull;

public class DLanguageRunner extends DefaultProgramRunner {
    public static final String DLANGUAGE_RUNNER_ID = "DLanguageRunner";

    @NotNull
    public String getRunnerId() {
        return DLANGUAGE_RUNNER_ID;
    }

    public boolean canRun(@NotNull final String executorId, @NotNull final RunProfile profile) {
        return executorId.equals(DefaultRunExecutor.EXECUTOR_ID) && profile instanceof DLanguageRunConfigurationBase;
    }
}

