package net.masterthought.dlanguage.run;


import com.intellij.execution.configuration.AbstractRunConfiguration;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RuntimeConfigurationError;
import com.intellij.execution.configurations.RuntimeConfigurationException;
import com.intellij.openapi.project.Project;
import net.masterthought.dlanguage.settings.DLanguageBuildSettings;

public abstract class DLanguageRunConfigurationBase extends AbstractRunConfiguration {
    public DLanguageRunConfigurationBase(Project project, ConfigurationFactory factory) {
        super(project, factory);
    }

    protected void requireDmd() throws RuntimeConfigurationException {
        final DLanguageBuildSettings buildSettings = DLanguageBuildSettings.getInstance(getProject());
        final String dmdPath = buildSettings.getDmdPath();
        if (dmdPath.isEmpty()) {
            throw new RuntimeConfigurationError("Path to cabal is not set.");
        }
    }


}

