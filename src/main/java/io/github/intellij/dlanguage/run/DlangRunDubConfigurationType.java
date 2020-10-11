package io.github.intellij.dlanguage.run;

import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import io.github.intellij.dlanguage.DlangBundle;
import io.github.intellij.dlanguage.icons.DlangIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DlangRunDubConfigurationType extends ConfigurationTypeBase {

    public DlangRunDubConfigurationType() {
        super("DlangRunDubConfigType",
            DlangBundle.INSTANCE.message("run.dub.text"),
            DlangBundle.INSTANCE.message("run.dub.descr"),
            DlangIcons.RUN);
        addFactory(new DLanguageFactory(this));
    }

    private static class DLanguageFactory extends DlangRunConfigurationFactory {

        public DLanguageFactory(final ConfigurationType type) {
            super(type);
        }

        @NotNull
        public RunConfiguration createTemplateConfiguration(@NotNull final Project project) {
            return new DlangRunDubConfiguration("DlangRunDubConfig", project, this);
        }

        @Override
        public boolean isApplicable(@NotNull Project project) {
            @Nullable final String file = FileUtil.findFileInProvidedPath(project.getBasePath(), "dub.json", "dub.sdl");
            return file != null && !file.isEmpty();
        }
    }
}
