package net.masterthought.dlanguage.unittest;

import com.intellij.execution.configuration.EnvironmentVariablesComponent;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.RawCommandLineEditor;
import net.masterthought.dlanguage.DLanguageBundle;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Map;

public class DUnitTestRunConfigurationEditor extends SettingsEditor<DUnitTestRunConfiguration> {

    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;

    // Common
    private JCheckBox cbRdmd;
    private JCheckBox cbForce;
    private JCheckBox cbNoDeps;
    private JCheckBox cbForceRemove;
    private JCheckBox cbCombined;
    private JCheckBox cbParallel;
    private JTextField tfBuild;
    private JTextField tfConfig;
    private JTextField tfArch;
    private JTextField tfDebug;
    private JTextField tfCompiler;
    private JComboBox tfBuildMode;
    private JCheckBox cbVerbose;
    private JCheckBox cbQuiet;

    // Run
    private JCheckBox cbTempBuild;

    // Test
    private JTextField tfMainFile;

    private TextFieldWithBrowseButton pathWorkingDir;
    private RawCommandLineEditor textParameters;
    private EnvironmentVariablesComponent envVariables;

    /**
     * Update editor UI with data of DLangRunDubConfiguration.
     * All components must be changed according to "config" data.
     */
    @Override
    protected void resetEditorFrom(DUnitTestRunConfiguration config) {
        resetGeneralTabForm(config);
    }


    /**
     * Save state of editor UI to DLangRunDubConfiguration instance.
     */
    @Override
    protected void applyEditorTo(DUnitTestRunConfiguration config) throws ConfigurationException {
        applyGeneralTabForm(config);
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        FileChooserDescriptor fcd = FileChooserDescriptorFactory.createSingleFolderDescriptor();
        fcd.setShowFileSystemRoots(true);
        fcd.setTitle(DLanguageBundle.message("dmd.run.config.selectworkingdir.title"));
        fcd.setDescription(DLanguageBundle.message("dmd.run.config.selectworkingdir.description"));
        fcd.setHideIgnored(false);

        pathWorkingDir.addBrowseFolderListener(null,
                new TextFieldWithBrowseButton.BrowseFolderActionListener<JTextField>(fcd.getTitle(), fcd.getDescription(),
                        pathWorkingDir, null, fcd, TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT));

        return panel1;
    }

    @Override
    protected void disposeEditor() {
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private void applyGeneralTabForm(DUnitTestRunConfiguration config) {
        config.setCbRdmd(cbRdmd.isSelected());
        config.setCbNoDeps(cbNoDeps.isSelected());
        config.setCbForce(cbForce.isSelected());
        config.setCbForceRemove(cbForceRemove.isSelected());
        config.setCbCombined(cbCombined.isSelected());
        config.setCbParallel(cbParallel.isSelected());
        config.setTfBuild(tfBuild.getText());
        config.setTfConfig(tfConfig.getText());
        config.setTfArch(tfArch.getText());
        config.setTfDebug(tfDebug.getText());
        config.setTfCompiler(tfCompiler.getText());
        config.setBuildMode(tfBuildMode.getSelectedIndex());
        config.setVerbose(cbVerbose.isSelected());
        config.setQuiet(cbQuiet.isSelected());

        config.setCbTempBuild(cbTempBuild.isSelected());
        config.setTfMainFile(tfMainFile.getText());

        config.setWorkingDir(pathWorkingDir.getText());
        config.setAdditionalParams(textParameters.getText());
        config.setEnvVars(envVariables.getEnvs());
    }

    private void resetGeneralTabForm(DUnitTestRunConfiguration config) {
        cbRdmd.setSelected(config.isCbRdmd());
        cbNoDeps.setSelected(config.isCbNoDeps());
        cbForce.setSelected(config.isCbForce());
        cbForceRemove.setSelected(config.isCbForceRemove());
        cbCombined.setSelected(config.isCbCombined());
        cbParallel.setSelected(config.isCbParallel());
        cbVerbose.setSelected(config.isVerbose());
        cbQuiet.setSelected(config.isQuiet());

        tfBuild.setText(config.getTfBuild());
        tfConfig.setText(config.getTfConfig());
        tfArch.setText(config.getTfArch());
        tfDebug.setText(config.getTfDebug());
        tfCompiler.setText(config.getTfCompiler());
        tfBuildMode.setSelectedIndex(config.getBuildMode());

        cbTempBuild.setSelected(config.isCbTempBuild());
        tfMainFile.setText(config.getTfMainFile());

        pathWorkingDir.setText(config.getWorkingDir());
        textParameters.setText(config.getAdditionalParams());
        Map<String, String> envVars = config.getEnvVars();
        if (envVars != null) {
            envVariables.setEnvs(config.getEnvVars());
        }
    }
}
