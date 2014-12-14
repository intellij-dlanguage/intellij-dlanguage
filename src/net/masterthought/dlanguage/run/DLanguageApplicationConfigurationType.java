package net.masterthought.dlanguage.run;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.util.containers.ContainerUtil;
import net.masterthought.dlanguage.DLanguageIcons;

public class DLanguageApplicationConfigurationType extends ConfigurationTypeBase {
    public static final String DLANGUAGE_CONFIG_ID = "D Language Application Configuration";

    protected DLanguageApplicationConfigurationType() {
        super(DLANGUAGE_CONFIG_ID, "D Language Application", "Run a D application.", DLanguageIcons.FILE);
        addFactory(new DLanguageApplicationConfigurationFactory(this));
    }

    public static DLanguageApplicationConfigurationType getInstance() {
        return ContainerUtil.findInstance(Extensions.getExtensions(CONFIGURATION_TYPE_EP), DLanguageApplicationConfigurationType.class);
    }
}
