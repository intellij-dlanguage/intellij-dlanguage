package io.github.intellij.dlanguage.project

import com.intellij.facet.ui.ValidationResult
import com.intellij.ide.util.projectWizard.AbstractNewProjectStep
import com.intellij.ide.util.projectWizard.CustomStepProjectGenerator
import com.intellij.ide.util.projectWizard.ProjectSettingsStepBase
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.impl.SdkConfigurationUtil
import com.intellij.openapi.roots.ui.configuration.SdkComboBox
import com.intellij.openapi.roots.ui.configuration.SdkComboBoxModel
import com.intellij.openapi.roots.ui.configuration.projectRoot.ProjectSdksModel
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.impl.welcomeScreen.AbstractActionWithPanel
import com.intellij.platform.DirectoryProjectGenerator
import com.intellij.platform.DirectoryProjectGeneratorBase
import com.intellij.platform.GeneratorPeerImpl
import com.intellij.platform.ProjectGeneratorPeer
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBPanel
import com.intellij.util.ui.components.BorderLayoutPanel
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangBundle.message
import io.github.intellij.dlanguage.project.ui.DubInitCheckBox
import io.github.intellij.dlanguage.sdk.DlangDmdSdkType
import io.github.intellij.dlanguage.sdk.DlangGdcSdkType
import io.github.intellij.dlanguage.sdk.DlangLdcSdkType
import java.awt.Color
import java.util.function.Predicate
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.Icon
import javax.swing.JComponent

/**
 * In IntelliJ IDEA we use ModuleBuilder but as that is only available in IntelliJ IDEA we have to use
 * DirectoryProjectGenerator which is available in all non-Java IDEs such as CLion, GoLand, and AppCode.
 *
 * The UI that this class defines should be similar to com.intellij.ide.util.projectWizard.ProjectJdkForModuleStep
 *
 * The intellij-rust plugin does similar here: https://github.com/mchernyavsky/intellij-rust/blob/master/src/main/kotlin/org/rust/ide/newProject/RsDirectoryProjectGenerator.kt
 */
class DlangProjectGenerator : DirectoryProjectGeneratorBase<DlangProjectSettings>(), CustomStepProjectGenerator<DlangProjectSettings> {

    private val settings: DlangProjectSettings = DlangProjectSettings()
    //private val gui: JComponent = createGui()

    override fun getName(): String = message("module.title")

    /**
     * Not sure where this description is used. It doesn't show up in CLion UI
     */
    override fun getDescription(): String? = message("module.description")

    override fun getHelpId(): String? = null

    override fun getLogo(): Icon? = DLanguage.Icons.SDK

    /**
     * Called when the project creation window is first used. Calls to createLazyPeer() will call this method
     */
    override fun createPeer(): ProjectGeneratorPeer<DlangProjectSettings> = DlangProjectGeneratorPeer(settings, createGui())

    private fun createGui(): JComponent {
        //val rootPanel = BorderLayoutPanel()
        val rootPanel = JBPanel<BorderLayoutPanel>()
        rootPanel.layout = BoxLayout(rootPanel, BoxLayout.PAGE_AXIS)

        val dmdPanel = BorderLayoutPanel()

        dmdPanel.border = BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(JBColor.LIGHT_GRAY, 1, true),
                "D compiler"
            ),
            BorderFactory.createEmptyBorder(10, 0, 20, 0)
        )

        val dlangSdkTypes = arrayOf(DlangDmdSdkType.SDK_TYPE_ID, DlangGdcSdkType.SDK_TYPE_ID, DlangLdcSdkType.SDK_TYPE_ID)

        val sdkComboBox = SdkComboBox(
            SdkComboBoxModel.createSdkComboBoxModel(
                ProjectManager.getInstance().defaultProject,
                ProjectSdksModel(), // cannot use ProjectStructureConfigurable as it's not in CLion, so cannot do ProjectStructureConfigurable.getInstance(ProjectManager.getInstance().defaultProject).projectJdksModel,
                Predicate { sdkTypeId -> dlangSdkTypes.contains(sdkTypeId.name) } // Condition { sdk: SdkTypeId -> sdk is DlangSdkType },
                //null,
                //null
            )
        )

        sdkComboBox.addActionListener { this.settings.sdk = (it.source as SdkComboBox).getSelectedSdk() }

        dmdPanel.add(sdkComboBox)

        rootPanel.add(dmdPanel)

        // use dub
        val dubPanel = BorderLayoutPanel()

        dubPanel.border = BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                "dub"
            ),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)
        )

        val dubInitCheckBox = DubInitCheckBox()
        dubInitCheckBox.addActionListener { this.settings.dubInit = (it.source as DubInitCheckBox).isSelected }

        dubPanel.add(dubInitCheckBox)

        rootPanel.add(dubPanel)

        return rootPanel
    }

    override fun generateProject(project: Project, baseDir: VirtualFile, settings: DlangProjectSettings, module: Module) {
        SdkConfigurationUtil.setDirectoryProjectSdk(project, settings.sdk)

        if(settings.dubInit) {
            // todo: run dub init
        }

//        val platformPrefix = PlatformUtils.getPlatformPrefix()
//        when(platformPrefix) {
//            PlatformUtils.APPCODE_PREFIX -> log.info("we have AppCode")
//            PlatformUtils.CLION_PREFIX -> log.info("we have CLion")
//            PlatformUtils.RIDER_PREFIX-> log.info("we have Rider")
//        }

        ApplicationManager.getApplication().runWriteAction {
            val srcFile = baseDir
                .createChildDirectory(this, "src")
                .createChildData(this, "app.d")

            VfsUtil.saveText(srcFile, """
                        |module ${project.name};
                        |import std.stdio;
                        |
                        |void main(string[] args) {
                        |    // todo
                        |}
                        |
                        |""".trimMargin()
            )
        }
    }

    // This method is called when clicking the Create button in the UI
    override fun validate(baseDirPath: String): ValidationResult =
        if(baseDirPath.isEmpty())
            ValidationResult("The base directory should not be empty")
        else ValidationResult.OK

    override fun createStep(projectGenerator: DirectoryProjectGenerator<DlangProjectSettings>?, callback: AbstractNewProjectStep.AbstractCallback<DlangProjectSettings>?): AbstractActionWithPanel {
        return DlangProjectSettingsStep(projectGenerator!!, AbstractNewProjectStep.AbstractCallback())
    }

}

class DlangProjectSettings {
    var sdk: Sdk? = null
    var dubInit = false  // TODO move this in dub and make dub add something to the build window instead
}

/**
 * For ideas on how to do this see:
 * https://github.com/mchernyavsky/intellij-rust/blob/master/src/main/kotlin/org/rust/ide/newProject/RsProjectGeneratorPeer.kt
 */
class DlangProjectGeneratorPeer(settings: DlangProjectSettings, ui: JComponent) : GeneratorPeerImpl<DlangProjectSettings>(settings, ui) {

    /**
     * This is called when clicking the Create button and will show the desired message in relation to a specified UI object
     */
    override fun validate(): ValidationInfo? {
        return if(settings.sdk == null)
            ValidationInfo("No SDK defined", this.component)
        else null
    }
}

class DlangProjectSettingsStep(gen: DirectoryProjectGenerator<DlangProjectSettings>,
                               callback: AbstractNewProjectStep.AbstractCallback<DlangProjectSettings>?)
    : ProjectSettingsStepBase<DlangProjectSettings>(gen, callback)
