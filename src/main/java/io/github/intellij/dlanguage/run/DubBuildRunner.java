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
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.project.DubConfigurationParser;
import io.github.intellij.dlanguage.project.DubPackage;
import io.github.intellij.dlanguage.project.DubProject;
import java.nio.file.Paths;

/**
 * Since {@link com.intellij.execution.runners.DefaultProgramRunner} is now deprecated this class was updated to use GenericProgramRunner.
 * It may be worth investigating the use of AsyncProgramRunner or perhaps simply the implementing
 * the ProgramRunner<Settings extends RunnerSettings> interface
 */
public class DubBuildRunner extends GenericProgramRunner<DubBuildRunner.DubBuildSettings> {

    @NotNull
    @Override
    public String getRunnerId() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean canRun(@NotNull String executorId, @NotNull RunProfile profile) {
        return (DefaultDebugExecutor.EXECUTOR_ID.equals(executorId) || DefaultRunExecutor.EXECUTOR_ID.equals(executorId)) && profile instanceof DlangRunDubConfiguration;
    }

    @Nullable
    @Override
    protected RunContentDescriptor doExecute(@NotNull final RunProfileState state, @NotNull final ExecutionEnvironment env) throws ExecutionException {
        if (env.getExecutor().getActionName().equals(DefaultDebugExecutor.EXECUTOR_ID)) {
            Project project = env.getProject();
            String executableFilePath = project.getBasePath().concat("/").concat(project.getName());

            final DubConfigurationParser dubParser = new DubConfigurationParser(project,
                ToolKey.DUB_KEY.getPath(), false);

            if (dubParser.canUseDub() && dubParser.getDubProject().isPresent()) {
                final DubProject dubProject = dubParser.getDubProject().get();
                final DubPackage dubPackage = dubProject.getRootPackage();
                executableFilePath = Paths.get(
                    dubPackage.getPath(), dubPackage.getTargetPath(), dubPackage.getTargetFileName()).toString().replace("\\", "/");
            }

            Executor executor = env.getExecutor();
            return RunUtil.startDebugger(this, state, env, project, executor, executableFilePath);
        }
        return super.doExecute(state, env);
    }

    public static class DubBuildSettings implements RunnerSettings {
        @Override
        public void readExternal(Element element) throws InvalidDataException {

        }

        @Override
        public void writeExternal(Element element) throws WriteExternalException {

        }
    }

}
