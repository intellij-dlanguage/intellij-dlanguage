package io.github.intellij.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.DefaultProgramRunner;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.RunContentDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DmdBuildRunner extends DefaultProgramRunner {
    @NotNull
    @Override
    public String getRunnerId() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean canRun(@NotNull String executorId, @NotNull RunProfile profile) {
        return (/*DefaultDebugExecutor.EXECUTOR_ID.equals(executorId) ||*/ DefaultRunExecutor.EXECUTOR_ID.equals(executorId)) &&
            profile instanceof DlangRunDmdConfiguration;
    }

    @Nullable
    @Override
    protected RunContentDescriptor doExecute(RunProfileState state, ExecutionEnvironment env) throws ExecutionException {
        /*if (env.getExecutor().getActionName().equals(DefaultDebugExecutor.EXECUTOR_ID)) {
            Project project = env.getProject();

            Executor executor = env.getExecutor();
            return RunUtil.startDebugger(this, state, env, project, executor);
        }*/
        return doExecute(state, env);
    }
}
