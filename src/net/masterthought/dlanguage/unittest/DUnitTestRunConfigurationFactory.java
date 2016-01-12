package net.masterthought.dlanguage.unittest;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class DUnitTestRunConfigurationFactory extends ConfigurationFactory
{
    protected DUnitTestRunConfigurationFactory(ConfigurationType type)
    {
        super(type);
    }

    @NotNull
    @Override
    public RunConfiguration createTemplateConfiguration(@NotNull Project project)
    {
        return new DUnitTestRunConfiguration(project,this,"configFactoryD");
    }
}
