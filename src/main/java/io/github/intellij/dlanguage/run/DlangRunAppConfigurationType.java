package io.github.intellij.dlanguage.run;

import com.intellij.execution.BeforeRunTask;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.NotNullLazyValue;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.DlangBundle;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DlangRunAppConfigurationType extends ConfigurationTypeBase {

    public DlangRunAppConfigurationType() {
        super("DlangRunAppConfigType",
            DlangBundle.INSTANCE.message("run.app.text"),
            DlangBundle.INSTANCE.message("run.app.descr"),
            DLanguage.Icons.RUN);
        addFactory(new DLanguageFactory(this));
    }

    private static class DLanguageFactory extends DlangRunConfigurationFactory {
        public DLanguageFactory(final ConfigurationType type) {
            super(
                type.getId(),
                type.getDisplayName(),
                type.getConfigurationTypeDescription(),
                NotNullLazyValue.createConstantValue(DLanguage.Icons.RUN)
            );
        }

        @NotNull
        public RunConfiguration createTemplateConfiguration(@NotNull final Project project) {
            return new DlangRunAppConfiguration("DlangRunAppConfig", project, this);
        }

        /**
         * Controls the default 'before launch' tasks that are configured in a run configuration
         *
         * @param providerID the key of the Task to check
         * @param task the actual Task
         */
        @Override
        public void configureBeforeRunTaskDefaults(Key<? extends BeforeRunTask> providerID, BeforeRunTask task) {
            // potentially we could enable DlangRunDmdConfigurationType.ID
            // to do that we'll need to change the overridden boolean values in DlangRunAppConfiguration and
            // also add a <stepsBeforeRunProvider implementation="DlangCompileBeforeRunProvider" id="dlangCompileBeforeRun"/>
            // to the plugin.xml and creating a BeforeRunTask which compiles the project.
            // see: https://stackoverflow.com/a/53979347/912408
        }
    }

}
