package io.github.intellij.dlanguage.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.configurations.RunnerSettings;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.GenericProgramRunner;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import io.github.intellij.dlanguage.run.exception.ModuleNotFoundException;
import io.github.intellij.dlanguage.run.exception.NoValidDlangSdkFound;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Since DefaultProgramRunner is deprecated this class was updated to use GenericProgramRunner.
 * It may be worth investigating the use of AsyncProgramRunner or perhaps simply the implementing
 * the ProgramRunner<Settings extends RunnerSettings> interface
 */
public class RunAppRunner extends GenericProgramRunner<RunAppRunner.DubAppSettings> {

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

    public static class DubAppSettings implements RunnerSettings {
        @Override
        public void readExternal(Element element) throws InvalidDataException {

        }

        @Override
        public void writeExternal(Element element) throws WriteExternalException {

        }
    }
}
