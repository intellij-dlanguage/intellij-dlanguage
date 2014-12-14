package net.masterthought.dlanguage.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class DLanguageApplicationConfigurationFactory extends ConfigurationFactory {
    protected DLanguageApplicationConfigurationFactory(@NotNull ConfigurationType type) {
        super(type);
    }

    @Override
    public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new DLanguageApplicationRunConfiguration(project, this);
    }
}
