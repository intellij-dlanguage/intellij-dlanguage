package io.github.intellij.dlanguage.project

import com.intellij.facet.ui.ValidationResult
import com.intellij.ide.util.projectWizard.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.impl.welcomeScreen.AbstractActionWithPanel
import com.intellij.platform.DirectoryProjectGenerator
import com.intellij.platform.DirectoryProjectGeneratorBase
import com.intellij.platform.GeneratorPeerImpl
import com.intellij.platform.ProjectGeneratorPeer
import com.intellij.ui.components.JBPanel
import com.intellij.util.PlatformUtils
import com.intellij.util.ui.components.BorderLayoutPanel
import io.github.intellij.dlanguage.icons.DlangIcons
import io.github.intellij.dlanguage.project.ui.DmdCompilerComboBox
import io.github.intellij.dlanguage.project.ui.DubInitCheckBox
import java.awt.Color
import javax.swing.*

/**
 * In IntelliJ IDEA we use ModuleBuilder but as that is only available in IntelliJ IDEA we have to use
 * DirectoryProjectGenerator which is available in all non-Java IDEs such as CLion, GoLand, and AppCode.
 */
class DlangProjectGenerator : DirectoryProjectGeneratorBase<Any>(), CustomStepProjectGenerator<Any> {

    private val log: Logger = Logger.getInstance(javaClass)

    override fun getName(): String = "new D Project"

    override fun getDescription(): String? = ""

    override fun getLogo(): Icon? = DlangIcons.SDK

    override fun createPeer(): ProjectGeneratorPeer<Any> = DlangProjectGeneratorPeer("", createGui())

    private fun createGui(): JComponent {
        //val rootPanel = BorderLayoutPanel()
        val rootPanel = JBPanel<BorderLayoutPanel>()
        rootPanel.layout = BoxLayout(rootPanel, BoxLayout.PAGE_AXIS)

        val dmdPanel = BorderLayoutPanel()

        dmdPanel.border = BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                "dmd"
            ),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)
        )
        dmdPanel.add(DmdCompilerComboBox())

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
        dubPanel.add(DubInitCheckBox())

        rootPanel.add(dubPanel)

        return rootPanel
    }

    override fun generateProject(project: Project, baseDir: VirtualFile, settings: Any, module: Module) {
//        val sdkType = DlangSdkType.getInstance()
//
//        val sdkComparator: Comparator<Sdk> = Comparator({ sdk1: Sdk, sdk2: Sdk ->
//            when (sdkType) {
//                sdk1.sdkType -> -1
//                sdk2.sdkType -> 1
//                else -> 0
//            }
//        })

        //SdkConfigurationUtil.configureDirectoryProjectSdk(project, sdkComparator, sdkType)
//        val dmd: Sdk? = SdkConfigurationUtil.findOrCreateSdk(sdkComparator, sdkType)
//
//        ApplicationManager.getApplication().run {
//            ProjectRootManager.getInstance(project).projectSdk = dmd
//        }

        val platformPrefix = PlatformUtils.getPlatformPrefix()
        when(platformPrefix) {
            PlatformUtils.APPCODE_PREFIX -> log.info("we have AppCode")
            PlatformUtils.CLION_PREFIX -> {
                log.info("we have CLion")

                // create a cmake file. The LDC project has a good example of using cmake with D:
                // https://github.com/ldc-developers/ldc
                ApplicationManager.getApplication().runWriteAction {
                    baseDir.createChildDirectory(this, "src")
                    baseDir.createChildData(this, "src/app.d")
                    val cmakeList = baseDir.createChildData(this, "CMakeLists.txt")
                    VfsUtil.saveText(cmakeList, """
                        |cmake_minimum_required(VERSION 2.8)
                        |
                        |project(${project.name})
                        |
                        |file(GLOB SOURCES "src/*.d")
                        |
                        |add_executable(${project.name} ${"$"}{SOURCES})""".trimMargin()
                    )
                }
            }
            PlatformUtils.RIDER_PREFIX-> log.info("we have Rider")
        }
    }

    override fun validate(baseDirPath: String): ValidationResult = ValidationResult.OK

    override fun createStep(projectGenerator: DirectoryProjectGenerator<Any>?, callback: AbstractNewProjectStep.AbstractCallback<Any>?): AbstractActionWithPanel {
        return DlangProjectSettingsStep(projectGenerator!!, AbstractNewProjectStep.AbstractCallback())
    }

}


class DlangProjectGeneratorPeer(settings: Any, ui: JComponent) : GeneratorPeerImpl<Any>(settings, ui)

class DlangProjectSettingsStep(gen: DirectoryProjectGenerator<Any>,
                               callback: AbstractNewProjectStep.AbstractCallback<Any>?)
    : ProjectSettingsStepBase<Any>(gen, callback)
