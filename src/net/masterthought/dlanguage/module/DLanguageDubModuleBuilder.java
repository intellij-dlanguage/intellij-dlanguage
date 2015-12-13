package net.masterthought.dlanguage.module;

import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ModuleBasedConfiguration;
import com.intellij.execution.impl.RunManagerImpl;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import net.masterthought.dlanguage.DLanguageBundle;
import net.masterthought.dlanguage.run.DLanguageRunDubConfigurationType;

public class DLanguageDubModuleBuilder extends DLanguageModuleBuilder {

    public static final String RUN_DUB_CONFIG_NAME = "Run DUB";

    public DLanguageDubModuleBuilder() {
        super("DLangDubApp", DLanguageBundle.message("module.dub.title"), DLanguageBundle.message("module.dub.description"), null);
    }

    @Override
    public void setupRootModel(ModifiableRootModel rootModel) throws ConfigurationException {
        setJavaRootModel(rootModel);

        Project project = rootModel.getProject();
        RunManagerImpl runManager = RunManagerImpl.getInstanceImpl(project);

        //Create "Run dub" configuration
        RunnerAndConfigurationSettings runDubSettings = runManager.findConfigurationByName(RUN_DUB_CONFIG_NAME);
        if(runDubSettings == null) {
            final DLanguageRunDubConfigurationType configurationType
                    = Extensions.findExtension(ConfigurationType.CONFIGURATION_TYPE_EP, DLanguageRunDubConfigurationType.class);
            final ConfigurationFactory factory = configurationType.getConfigurationFactories()[0];
            runDubSettings = runManager.createRunConfiguration(RUN_DUB_CONFIG_NAME, factory);
            ((ModuleBasedConfiguration) runDubSettings.getConfiguration()).setModule(rootModel.getModule());

            runManager.addConfiguration(runDubSettings, false);
        }
    }
}
