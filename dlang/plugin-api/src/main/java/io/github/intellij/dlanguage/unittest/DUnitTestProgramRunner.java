package io.github.intellij.dlanguage.unittest;

import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunnerSettings;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.GenericProgramRunner;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

/**
 * Since {@link com.intellij.execution.runners.DefaultProgramRunner} is now deprecated this class was updated to use GenericProgramRunner.
 * It may be worth investigating the use of AsyncProgramRunner or perhaps simply the implementing
 * the ProgramRunner<Settings extends RunnerSettings> interface
 */
public class DUnitTestProgramRunner extends GenericProgramRunner<DUnitTestProgramRunner.DUnitTestSettings> {

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

    public static class DUnitTestSettings implements RunnerSettings {
        @Override
        public void readExternal(Element element) throws InvalidDataException {

        }

        @Override
        public void writeExternal(Element element) throws WriteExternalException {

        }
    }
}
