package io.github.intellij.dub.run

import com.intellij.application.options.ModulesComboBox
import com.intellij.execution.configuration.EnvironmentVariablesComponent
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.ui.TextComponentAccessor
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.util.text.Strings
import com.intellij.ui.RawCommandLineEditor
import io.github.intellij.dlanguage.DlangBundle.message
import io.github.intellij.dlanguage.module.DlangModuleType
import javax.swing.*

class DlangRunDubConfigurationEditor(val project: Project) : SettingsEditor<DlangRunDubConfiguration>() {

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
    private lateinit var tfCompiler: TextFieldWithBrowseButton
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
     *
     * This function will be invoked repeatedly so override isReadyForApply() to implement logic to
     * actually check that values have changed
     */
    @Throws(ConfigurationException::class)
    override fun applyEditorTo(config: DlangRunDubConfiguration) {
        if (this.isReadyForApply) {
            applyGeneralTabForm(config)
        }
    }

    // todo: Have isReadyForApply check if fields have changed
    override fun isReadyForApply(): Boolean {
        return true
    }

    override fun createEditor(): JComponent {
        this.pathWorkingDir.addBrowseFolderListener(
            this.project,
            FileChooserDescriptorFactory.createSingleFolderDescriptor()
                .withTitle(message("dmd.run.config.selectworkingdir.title"))
                .withDescription(message("dmd.run.config.selectworkingdir.description"))
                .withShowFileSystemRoots(true)
                .withHideIgnored(false),
            TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT
        )

        this.tfCompiler.addBrowseFolderListener(
            this.project,
            FileChooserDescriptorFactory.createSingleFileOrExecutableAppDescriptor()
                .withTitle(message("d.ui.dmd.config.choosebinary"))
                .withDescription(message("d.ui.dmd.config.choosebinary.desc"))
                .withShowFileSystemRoots(true)
                .withHideIgnored(true),
            TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT)

        return myMainPanel
    }

    override fun disposeEditor() {
        // call dispose() on the fields with Action Listeners
        this.pathWorkingDir.dispose();
        this.tfCompiler.dispose();
    }

    private fun createUIComponents() {
        // TODO: place custom component creation code here
    }

    private fun applyGeneralTabForm(config: DlangRunDubConfiguration) {
        config.setModule(comboModules.selectedModule)
        config.generalDubOptions = comboGeneralDubOptions.selectedIndex
        val inBuildState = comboGeneralDubOptions.selectedIndex == 0
        val inRunState = comboGeneralDubOptions.selectedIndex == 1
        val inTestState = comboGeneralDubOptions.selectedIndex == 2
        cbTempBuild.isEnabled = inRunState
        cbCoverage.isEnabled = inTestState
        tfMainFile.isEnabled = inTestState
        cbRdmd.isEnabled = inBuildState || inRunState
        cbParallel.isEnabled = inBuildState || inRunState
        config.isCbRdmd = cbRdmd.isSelected
        config.isCbNoDeps = cbNoDeps.isSelected
        config.isCbForce = cbForce.isSelected
        config.isCbForceRemove = cbForceRemove.isSelected
        config.isCbCombined = cbCombined.isSelected
        config.isCbParallel = cbParallel.isSelected
        config.tfBuild = tfBuild.text
        config.tfConfig = tfConfig.text
        config.tfArch = tfArch.text
        config.tfDebug = tfDebug.text

        config.tfCompiler = if (this.tfCompiler.text.isBlank()) {
            Strings.notNullize(ProjectRootManager.getInstance(project).projectSdk?.homePath)
        } else {
            tfCompiler.text
        }

        config.buildMode = tfBuildMode.selectedIndex
        config.isVerbose = cbVerbose.isSelected
        config.isQuiet = cbQuiet.isSelected
        config.isCbTempBuild = cbTempBuild.isSelected
        config.isCbCoverage = cbCoverage.isSelected
        config.tfMainFile = tfMainFile.text
        config.workingDir = pathWorkingDir.text
        config.additionalParams = textParameters.text
        config.envVars = envVariables.envs
    }

    private fun resetGeneralTabForm(config: DlangRunDubConfiguration) {
        comboModules.fillModules(config.project, DlangModuleType.getInstance())
        comboModules.selectedModule = config.configurationModule?.module
        comboGeneralDubOptions.selectedIndex = config.generalDubOptions
        cbRdmd.isSelected = config.isCbRdmd
        cbNoDeps.isSelected = config.isCbNoDeps
        cbForce.isSelected = config.isCbForce
        cbForceRemove.isSelected = config.isCbForceRemove
        cbCombined.isSelected = config.isCbCombined
        cbParallel.isSelected = config.isCbParallel
        cbVerbose.isSelected = config.isVerbose
        cbQuiet.isSelected = config.isQuiet
        tfBuild.text = config.tfBuild
        tfConfig.text = config.tfConfig
        tfArch.text = config.tfArch
        tfDebug.text = config.tfDebug

        this.tfCompiler.text = if (config.tfCompiler.isNullOrBlank()) {
            Strings.notNullize(ProjectRootManager.getInstance(project).projectSdk?.homePath)
        } else {
            Strings.notNullize(config.tfCompiler).trim()
        }

        tfBuildMode.selectedIndex = config.buildMode
        cbTempBuild.isSelected = config.isCbTempBuild
        tfMainFile.text = config.tfMainFile
        cbCoverage.isSelected = config.isCbCoverage
        pathWorkingDir.setText(config.workingDir)
        textParameters.text = config.additionalParams

        config.envVars?.let { envVars ->
            envVariables.envs = envVars
        }
    }
}
