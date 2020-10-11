package io.github.intellij.dlanguage.run;

import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import io.github.intellij.dlanguage.DlangBundle;
import io.github.intellij.dlanguage.icons.DlangIcons;
import org.jetbrains.annotations.NotNull;

public class DlangRunDmdConfigurationType extends ConfigurationTypeBase {

    public DlangRunDmdConfigurationType() {
        super("DlangRunDmdConfigType",
            DlangBundle.INSTANCE.message("run.dmd.text"),
            DlangBundle.INSTANCE.message("run.dmd.descr"),
            DlangIcons.RUN);
        addFactory(new DLanguageFactory(this));
    }

    private static class DLanguageFactory extends DlangRunConfigurationFactory {
        public DLanguageFactory(final ConfigurationType type) {
            super(type);
        }

        @NotNull
        public RunConfiguration createTemplateConfiguration(@NotNull final Project project) {
            return new DlangRunDmdConfiguration("DlangRunDmdConfig", project, this);
        }
    }
}
