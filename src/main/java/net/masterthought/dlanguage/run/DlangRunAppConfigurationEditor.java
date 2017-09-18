package net.masterthought.dlanguage.run;

import com.intellij.application.options.ModulesComboBox;
import com.intellij.execution.configuration.EnvironmentVariablesComponent;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.RawCommandLineEditor;
import net.masterthought.dlanguage.DLanguageBundle;
import net.masterthought.dlanguage.module.DlangModuleType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Map;

public class DlangRunAppConfigurationEditor extends SettingsEditor<DlangRunAppConfiguration> {
    private JPanel myMainPanel;
    private ModulesComboBox comboModule;
    private RawCommandLineEditor textParameters;
    private JPanel appPathPanel;
    private EnvironmentVariablesComponent envVariables;
    private TextFieldWithBrowseButton pathWorkingDir;
    private JLabel appPathLabel;

    /**
     * Update editor UI with data of DLangRunAppConfiguration.
     * All components must be changed according to "config" data.
     */
    @Override
    protected void resetEditorFrom(final DlangRunAppConfiguration config) {
        comboModule.fillModules(config.getProject(), DlangModuleType.getInstance());
        comboModule.setSelectedModule(config.getConfigurationModule().getModule());
        pathWorkingDir.setText(config.getWorkDir());
        textParameters.setText(config.getAdditionalParams());
        final Map<String, String> envVars = config.getEnvVars();
        if (envVars != null) {
            envVariables.setEnvs(config.getEnvVars());
        }
        appPathLabel.setText(config.getExecutablePath());
    }


    /**
     * Save state of editor UI to DLangRunAppConfiguration instance.
     */
    @Override
    protected void applyEditorTo(final DlangRunAppConfiguration config) throws ConfigurationException {
        config.setModule(comboModule.getSelectedModule());
        config.setWorkDir(pathWorkingDir.getText());
        config.setAdditionalParams(textParameters.getText());
        config.setEnvVars(envVariables.getEnvs());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        final FileChooserDescriptor fcd = FileChooserDescriptorFactory.createSingleFolderDescriptor();
        fcd.setShowFileSystemRoots(true);
        fcd.setTitle(DLanguageBundle.INSTANCE.message("dmd.run.config.selectworkingdir.title"));
        fcd.setDescription(DLanguageBundle.INSTANCE.message("dmd.run.config.selectworkingdir.description"));
        fcd.setHideIgnored(false);

        pathWorkingDir.addActionListener(new TextFieldWithBrowseButton.BrowseFolderActionListener<>(fcd.getTitle(), fcd.getDescription(),
            pathWorkingDir, null, fcd, TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT));

        return myMainPanel;
    }

    @Override
    protected void disposeEditor() {
    }
}
