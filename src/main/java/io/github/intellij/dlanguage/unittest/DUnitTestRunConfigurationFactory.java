package io.github.intellij.dlanguage.unittest;

import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NotNullLazyValue;
import io.github.intellij.dlanguage.run.DlangRunConfigurationFactory;
import org.jetbrains.annotations.NotNull;

public class DUnitTestRunConfigurationFactory extends DlangRunConfigurationFactory {

    /**
     * Constructed by DUnitTestRunConfigurationType
     *
     * @param type ConfigurationType (the DUnitTestRunConfigurationType that creates this factory)
     */
    protected DUnitTestRunConfigurationFactory(final ConfigurationType type) {
        super(
            type.getId(),
            type.getDisplayName(),
            type.getConfigurationTypeDescription(),
            NotNullLazyValue.createConstantValue(type.getIcon())
        );
    }

    @NotNull
    @Override
    public RunConfiguration createTemplateConfiguration(@NotNull final Project project) {
        return new DUnitTestRunConfiguration("DlangTestConfig", project);
    }
}
