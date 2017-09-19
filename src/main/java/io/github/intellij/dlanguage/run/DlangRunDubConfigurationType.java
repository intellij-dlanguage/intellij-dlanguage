package io.github.intellij.dlanguage.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import io.github.intellij.dlanguage.DlangBundle;
import io.github.intellij.dlanguage.icons.DlangIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DlangRunDubConfigurationType implements ConfigurationType {
    private final DLanguageFactory myConfigurationFactory;

    public DlangRunDubConfigurationType() {
        myConfigurationFactory = new DLanguageFactory(this);
    }

    @Override
    public String getDisplayName() {
        return DlangBundle.INSTANCE.message("run.dub.text");
    }

    @Override
    public String getConfigurationTypeDescription() {
        return DlangBundle.INSTANCE.message("run.dub.descr");
    }

    @Override
    public Icon getIcon() {
        return DlangIcons.FILE;
    }

    @NotNull
    @Override
    public String getId() {
        return "DlangRunDubConfiguration";
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
            return new DlangRunDubConfiguration("Build with DUB", project, this);
        }
    }
}
