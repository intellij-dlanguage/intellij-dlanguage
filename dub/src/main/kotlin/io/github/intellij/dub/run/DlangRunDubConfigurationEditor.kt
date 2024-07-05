package io.github.intellij.dub.run

import com.intellij.application.options.ModulesComboBox
import com.intellij.execution.configuration.EnvironmentVariablesComponent
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.ui.ComponentWithBrowseButton.BrowseFolderActionListener
import com.intellij.openapi.ui.TextComponentAccessor
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.RawCommandLineEditor
import io.github.intellij.dlanguage.DlangBundle.message
import io.github.intellij.dlanguage.module.DlangModuleType
import javax.swing.*

class DlangRunDubConfigurationEditor : SettingsEditor<DlangRunDubConfiguration>() {
    private lateinit var myMainPanel: JTabbedPane
    private lateinit var panel1: JPanel
    private lateinit var tabGeneral: JPanel

    //General tab
    private lateinit var comboModules: ModulesComboBox
    private lateinit var comboGeneralDubOptions: JComboBox<*>

    // Common (note that some fields have tooltips configured in DlangRunDubConfigurationEditor.form)
    private lateinit var cbRdmd: JCheckBox
    private lateinit var cbForce: JCheckBox
    private lateinit var cbNoDeps: JCheckBox
    private lateinit var cbForceRemove: JCheckBox
    private lateinit var cbCombined: JCheckBox
    private lateinit var cbParallel: JCheckBox
    private lateinit var tfBuild: JTextField
    private lateinit var tfConfig: JTextField
    private lateinit var tfArch: JTextField
    private lateinit var tfDebug: JTextField
    private lateinit var tfCompiler: JTextField
    private lateinit var tfBuildMode: JComboBox<*>
    private lateinit var cbVerbose: JCheckBox
    private lateinit var cbQuiet: JCheckBox

    // Run
    private lateinit var cbTempBuild: JCheckBox

    // Test
    private lateinit var tfMainFile: JTextField
    private lateinit var cbCoverage: JCheckBox
    private lateinit var pathWorkingDir: TextFieldWithBrowseButton
    private lateinit var textParameters: RawCommandLineEditor
    private lateinit var envVariables: EnvironmentVariablesComponent

    /**
     * Update editor UI with data of DLangRunDubConfiguration.
     * All components must be changed according to "config" data.
     */
    override fun resetEditorFrom(config: DlangRunDubConfiguration) {
        resetGeneralTabForm(config)
    }

    /**
     * Save state of editor UI to DLangRunDubConfiguration instance.
     */
    @Throws(ConfigurationException::class)
    override fun applyEditorTo(config: DlangRunDubConfiguration) {
        applyGeneralTabForm(config)
    }

    override fun createEditor(): JComponent {
        val fcd = FileChooserDescriptorFactory.createSingleFolderDescriptor()
        fcd.isShowFileSystemRoots = true
        fcd.title = message("dmd.run.config.selectworkingdir.title")
        fcd.description = message("dmd.run.config.selectworkingdir.description")
        fcd.isHideIgnored = false
        pathWorkingDir!!.addActionListener(
            BrowseFolderActionListener(
                fcd.title, fcd.description,
                pathWorkingDir, null, fcd, TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT
            )
        )
        return myMainPanel!!
    }

    override fun disposeEditor() {}
    private fun createUIComponents() {
        // TODO: place custom component creation code here
    }

    private fun applyGeneralTabForm(config: DlangRunDubConfiguration) {
        config.setModule(comboModules!!.selectedModule)
        config.generalDubOptions = comboGeneralDubOptions!!.selectedIndex
        val inBuildState = comboGeneralDubOptions.selectedIndex == 0
        val inRunState = comboGeneralDubOptions.selectedIndex == 1
        val inTestState = comboGeneralDubOptions.selectedIndex == 2
        cbTempBuild!!.isEnabled = inRunState
        cbCoverage!!.isEnabled = inTestState
        tfMainFile!!.isEnabled = inTestState
        cbRdmd!!.isEnabled = inBuildState || inRunState
        cbParallel!!.isEnabled = inBuildState || inRunState
        config.isCbRdmd = cbRdmd.isSelected
        config.isCbNoDeps = cbNoDeps!!.isSelected
        config.isCbForce = cbForce!!.isSelected
        config.isCbForceRemove = cbForceRemove!!.isSelected
        config.isCbCombined = cbCombined!!.isSelected
        config.isCbParallel = cbParallel.isSelected
        config.tfBuild = tfBuild!!.text
        config.tfConfig = tfConfig!!.text
        config.tfArch = tfArch!!.text
        config.tfDebug = tfDebug!!.text
        config.tfCompiler = tfCompiler!!.text
        config.buildMode = tfBuildMode!!.selectedIndex
        config.isVerbose = cbVerbose!!.isSelected
        config.isQuiet = cbQuiet!!.isSelected
        config.isCbTempBuild = cbTempBuild.isSelected
        config.isCbCoverage = cbCoverage.isSelected
        config.tfMainFile = tfMainFile.text
        config.workingDir = pathWorkingDir!!.text
        config.additionalParams = textParameters!!.text
        config.envVars = envVariables!!.envs
    }

    private fun resetGeneralTabForm(config: DlangRunDubConfiguration) {
        comboModules!!.fillModules(config.project, DlangModuleType.getInstance())
        comboModules.selectedModule = config.configurationModule!!.module
        comboGeneralDubOptions!!.selectedIndex = config.generalDubOptions
        cbRdmd!!.isSelected = config.isCbRdmd
        cbNoDeps!!.isSelected = config.isCbNoDeps
        cbForce!!.isSelected = config.isCbForce
        cbForceRemove!!.isSelected = config.isCbForceRemove
        cbCombined!!.isSelected = config.isCbCombined
        cbParallel!!.isSelected = config.isCbParallel
        cbVerbose!!.isSelected = config.isVerbose
        cbQuiet!!.isSelected = config.isQuiet
        tfBuild!!.text = config.tfBuild
        tfConfig!!.text = config.tfConfig
        tfArch!!.text = config.tfArch
        tfDebug!!.text = config.tfDebug
        tfCompiler!!.text = config.tfCompiler
        tfBuildMode!!.selectedIndex = config.buildMode
        cbTempBuild!!.isSelected = config.isCbTempBuild
        tfMainFile!!.text = config.tfMainFile
        cbCoverage!!.isSelected = config.isCbCoverage
        pathWorkingDir!!.setText(config.workingDir)
        textParameters!!.text = config.additionalParams
        val envVars = config.envVars
        if (envVars != null) {
            envVariables!!.envs = config.envVars!!
        }
    }
}
