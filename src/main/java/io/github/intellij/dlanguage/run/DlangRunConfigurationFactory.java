package io.github.intellij.dlanguage.run;

import com.intellij.execution.BeforeRunTask;
import com.intellij.execution.configurations.RunConfigurationSingletonPolicy;
import com.intellij.execution.configurations.SimpleConfigurationType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Base ConfigurationType class for D language configuration types that are used for running dub, dmd, or run a compiled binary
 *
 * @author Samael Bate (singingbush)
 * created on 11/10/2020
 */
public abstract class DlangRunConfigurationFactory extends SimpleConfigurationType {

    public DlangRunConfigurationFactory(
        @NotNull String id,
        @Nls @NotNull String name,
        @Nls @Nullable String description,
        @NotNull NotNullLazyValue<Icon> icon
    ) {
        super(id, name, description, icon);
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
     * Controls the default 'before launch' tasks that are configured in a run configuration
     * <p>
     * Implemented this method so that we could suppress the /out directory being built:
     * <a href="https://github.com/intellij-dlanguage/intellij-dlanguage/issues/489">issue 489</a>
     * <a href="https://github.com/intellij-dlanguage/intellij-dlanguage/issues/1215">issue 1215</a>
     * </p>
     *
     * @param providerID the key of the Task to check
     * @param task the actual Task
     */
    @Override
    public void configureBeforeRunTaskDefaults(Key<? extends BeforeRunTask> providerID, BeforeRunTask task) {
        // by default, don't have any of our run configurations setup tasks.
        // potentially the Run App config could override this to do a compile before running the binary
        task.setEnabled(false);
    }
}
