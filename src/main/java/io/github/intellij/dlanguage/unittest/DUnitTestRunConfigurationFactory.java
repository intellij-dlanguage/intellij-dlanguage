package io.github.intellij.dlanguage.unittest;

import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import io.github.intellij.dlanguage.run.DlangRunConfigurationFactory;
import org.jetbrains.annotations.NotNull;

public class DUnitTestRunConfigurationFactory extends DlangRunConfigurationFactory {

    protected DUnitTestRunConfigurationFactory(final ConfigurationType type) {
        super(type);
    }

    @NotNull
    @Override
    public RunConfiguration createTemplateConfiguration(@NotNull final Project project) {
        return new DUnitTestRunConfiguration(project);
    }
}
