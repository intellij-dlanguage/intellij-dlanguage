package net.masterthought.dlanguage.unittest;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.openapi.extensions.Extensions;
import net.masterthought.dlanguage.icons.DlangIcons;

public class DUnitTestRunConfigurationType extends ConfigurationTypeBase {

    public static DUnitTestRunConfigurationType getInstance() {
        return Extensions.findExtension(CONFIGURATION_TYPE_EP, DUnitTestRunConfigurationType.class);
    }

    protected DUnitTestRunConfigurationType() {
        super("D",
                "dUnit Unit Tests",
                "dUnit unit tests run configuration",
                DlangIcons.RUN);
        addFactory(new DUnitTestRunConfigurationFactory(this));
    }
}
