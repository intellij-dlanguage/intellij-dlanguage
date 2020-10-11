package io.github.intellij.dlanguage.unittest;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import io.github.intellij.dlanguage.icons.DlangIcons;

public class DUnitTestRunConfigurationType extends ConfigurationTypeBase {

    public static DUnitTestRunConfigurationType getInstance() {
        return CONFIGURATION_TYPE_EP.findExtensionOrFail(DUnitTestRunConfigurationType.class);
    }

    protected DUnitTestRunConfigurationType() {
        super("DlangTestConfigType",
                "dUnit Unit Tests",
                "dUnit unit tests run configuration",
                DlangIcons.RUN);
        addFactory(new DUnitTestRunConfigurationFactory(this));
    }
}
