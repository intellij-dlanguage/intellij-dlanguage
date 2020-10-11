package io.github.intellij.dlanguage.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import io.github.intellij.dlanguage.DlangBundle;
import io.github.intellij.dlanguage.icons.DlangIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DlangRunDmdConfigurationType implements ConfigurationType {

    private final DLanguageFactory myConfigurationFactory;

    public DlangRunDmdConfigurationType() {
        myConfigurationFactory = new DLanguageFactory(this);
    }

    @NotNull
    @Override
    public String getId() {
        return "DlangRunDmdConfiguration";
    }

    @Override
    public String getDisplayName() {
        return DlangBundle.INSTANCE.message("run.dmd.text");
    }

    @Override
    public String getConfigurationTypeDescription() {
        return DlangBundle.INSTANCE.message("run.dmd.descr");
    }

    @Override
    public Icon getIcon() {
        return DlangIcons.FILE;
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{myConfigurationFactory};
    }

    private static class DLanguageFactory extends DlangRunConfigurationFactory {
        public DLanguageFactory(final ConfigurationType type) {
            super(type);
        }

        @NotNull
        public RunConfiguration createTemplateConfiguration(@NotNull final Project project) {
            return new DlangRunDmdConfiguration("Compile with DMD", project, this);
        }
    }
}
