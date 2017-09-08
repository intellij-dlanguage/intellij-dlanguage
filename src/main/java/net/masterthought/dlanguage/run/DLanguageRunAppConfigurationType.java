package net.masterthought.dlanguage.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import net.masterthought.dlanguage.DLanguageBundle;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DLanguageRunAppConfigurationType implements ConfigurationType {

    private final DLanguageFactory myConfigurationFactory;

    public DLanguageRunAppConfigurationType() {
        myConfigurationFactory = new DLanguageFactory(this);
    }

    @Override
    public String getDisplayName() {
        return DLanguageBundle.INSTANCE.message("run.app.text");
    }

    @Override
    public String getConfigurationTypeDescription() {
        return DLanguageBundle.INSTANCE.message("run.app.descr");
    }

    @Override
    public Icon getIcon() {
        return DLanguageIcons.FILE;
    }

    @NotNull
    @Override
    public String getId() {
        return "DLanguageRunAppConfiguration";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{myConfigurationFactory};
    }

    private static class DLanguageFactory extends ConfigurationFactory {
        public DLanguageFactory(final ConfigurationType type) {
            super(type);
        }

        public RunConfiguration createTemplateConfiguration(final Project project) {
            return new DLanguageRunAppConfiguration("Run D App", project, this);
        }
    }
}
