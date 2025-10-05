package io.github.intellij.dlanguage.run;

import com.intellij.execution.BeforeRunTask;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfigurationSingletonPolicy;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Samael Bate (singingbush)
 * created on 11/10/2020
 */
public abstract class DlangRunConfigurationFactory extends ConfigurationFactory {

    protected DlangRunConfigurationFactory(@NotNull ConfigurationType type) {
        super(type);
    }

    @NotNull @NonNls
    @Override
    public String getId() {
        return this.getType().getId(); // needed as calling the super.getId() is deprecated
    }

    @NotNull
    @Override
    public String getName() {
        return this.getType().getDisplayName();
    }

    @Override
    public boolean isApplicable(@NotNull Project project) {
        return true; // todo: only return true if this project is using D language
    }

    @Override
    public @NotNull RunConfigurationSingletonPolicy getSingletonPolicy() {
        return RunConfigurationSingletonPolicy.SINGLE_INSTANCE_ONLY; // so can't be run more than once at same time
    }

    /**
     * Implemented this method so that we could suppress the /out directory being built:
     * https://github.com/intellij-dlanguage/intellij-dlanguage/issues/489
     * @param providerID the key of the Task to check
     * @param task the actual Task
     */
    @Override
    public void configureBeforeRunTaskDefaults(Key<? extends BeforeRunTask> providerID, BeforeRunTask task) {
        super.configureBeforeRunTaskDefaults(providerID, task);
    }
}
