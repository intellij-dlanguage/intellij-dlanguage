package net.masterthought.dlanguage.run;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DLanguageApplicationRunConfigurationEditorForm extends SettingsEditor<DLanguageApplicationRunConfiguration> {
    private JPanel mainPanel;
    private com.intellij.ui.RawCommandLineEditor programArguments;

    @Override
    protected void resetEditorFrom(DLanguageApplicationRunConfiguration dLanguageRunConfiguration) {
        programArguments.setText(dLanguageRunConfiguration.programArguments);
    }

    @Override
    protected void applyEditorTo(DLanguageApplicationRunConfiguration dLanguageRunConfiguration) throws ConfigurationException {
        dLanguageRunConfiguration.programArguments = programArguments.getText();
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return mainPanel;
    }
}

