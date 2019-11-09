package io.github.intellij.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.DefaultProgramRunner;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import io.github.intellij.dlanguage.run.exception.ModuleNotFoundException;
import io.github.intellij.dlanguage.run.exception.NoValidDlangSdkFound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RunAppRunner extends DefaultProgramRunner {
    @NotNull
    @Override
    public String getRunnerId() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean canRun(@NotNull String executorId, @NotNull RunProfile profile) {
        return (DefaultDebugExecutor.EXECUTOR_ID.equals(executorId) || DefaultRunExecutor.EXECUTOR_ID.equals(executorId)) &&
            profile instanceof DlangRunAppConfiguration;
    }

    @Nullable
    @Override
    protected RunContentDescriptor doExecute(@NotNull final RunProfileState state, final ExecutionEnvironment env) throws ExecutionException {
        if (env.getExecutor().getActionName().equals(DefaultDebugExecutor.EXECUTOR_ID)) {
            final Project project = env.getProject();

            final Executor executor = env.getExecutor();
            final Logger log = Logger.getInstance(this.getClass());
            try {
                final DlangRunAppState dlangRunAppState = (DlangRunAppState) state;
                return RunUtil.startDebugger(this, state, env, project, executor, dlangRunAppState.getExecutableCommandLine(dlangRunAppState.getConfig()).getExePath());//todo this is yucky
            } catch (final ModuleNotFoundException e) {
                e.printStackTrace();
                log.error(e.toString());
            } catch (final NoValidDlangSdkFound e) {
                log.warn("Unable to run with DMD", e);
            }
        }
        return super.doExecute(state, env);
    }
}
