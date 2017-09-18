package net.masterthought.dlanguage.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import net.masterthought.dlanguage.DLanguageBundle;
import net.masterthought.dlanguage.icons.DlangIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DlangRunAppConfigurationType implements ConfigurationType {

    private final DLanguageFactory myConfigurationFactory;

    public DlangRunAppConfigurationType() {
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
        return DlangIcons.FILE;
    }

    @NotNull
    @Override
    public String getId() {
        return "DlangRunAppConfiguration";
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
            return new DlangRunAppConfiguration("Run D App", project, this);
        }
    }
}
