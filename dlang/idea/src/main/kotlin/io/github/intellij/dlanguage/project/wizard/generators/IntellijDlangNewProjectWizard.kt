package io.github.intellij.dlanguage.project.wizard.generators

import com.intellij.ide.highlighter.ModuleFileType
import com.intellij.ide.projectWizard.NewProjectWizardCollector.BuildSystem.logSdkChanged
import com.intellij.ide.projectWizard.NewProjectWizardCollector.BuildSystem.logSdkFinished
import com.intellij.ide.projectWizard.generators.AssetsNewProjectWizardStep
import com.intellij.ide.projectWizard.generators.IntelliJNewProjectWizardStep
import com.intellij.ide.starters.local.StandardAssetsProvider
import com.intellij.ide.wizard.NewProjectWizardChainStep.Companion.nextStep
import com.intellij.ide.wizard.NewProjectWizardStep
import com.intellij.ide.wizard.NewProjectWizardStep.Companion.MODIFIABLE_MODULE_MODEL_KEY
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.SdkTypeId
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.roots.ui.configuration.sdkComboBox
import com.intellij.openapi.util.io.FileUtil
import com.intellij.ui.dsl.builder.*
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangSdkType
import io.github.intellij.dlanguage.module.DlangModuleBuilder
import io.github.intellij.dlanguage.project.wizard.DlangNewProjectWizard
import io.github.intellij.dlanguage.project.wizard.generator.BuildSystemDlangNewProjectWizard
import io.github.intellij.dlanguage.project.wizard.generator.BuildSystemDlangNewProjectWizardData
import io.github.intellij.dlanguage.project.wizard.withDlangSampleCodeAsset
import java.nio.file.Paths


internal class IntellijDlangNewProjectWizard : BuildSystemDlangNewProjectWizard {

    override val name = "IntelliJ"

    override val ordinal = 0

    override fun createStep(parent: DlangNewProjectWizard.NewDlangProjectStep): NewProjectWizardStep =
        DlangStep(parent)
            .nextStep(::AssetStep)

    private class DlangStep(parent: DlangNewProjectWizard.NewDlangProjectStep) :
        IntelliJNewProjectWizardStep<DlangNewProjectWizard.NewDlangProjectStep>(parent),
        BuildSystemDlangNewProjectWizardData by parent,
        IntelliJDlangNewProjectWizardData {

        override fun setupSettingsUI(builder: Panel) {
            setupDlangSdkUI(builder)
            setupSampleCodeUI(builder)
        }

        override fun setupAdvancedSettingsUI(builder: Panel) {
            setupModuleNameUI(builder)
            setupModuleContentRootUI(builder)
            setupModuleFileLocationUI(builder)
        }

        override fun setupProject(project: Project) {
            val builder = DlangModuleBuilder()
            val moduleFile = Paths.get(moduleFileLocation, moduleName + ModuleFileType.DOT_DEFAULT_EXTENSION)

            builder.name = moduleName
            builder.moduleFilePath = FileUtil.toSystemDependentName(moduleFile.toString())
            builder.contentEntryPath = FileUtil.toSystemDependentName(contentRoot)

            if (context.isCreatingNewProject) {
                // New project with a single module: set project JDK
                context.projectJdk = sdk
            }
            else {
                // New module in an existing project: set module JDK
                val isSameSdk = ProjectRootManager.getInstance(project).projectSdk?.name == sdk?.name
                builder.moduleJdk = if (isSameSdk) null else sdk
            }

            val model = context.getUserData(MODIFIABLE_MODULE_MODEL_KEY)
            builder.commit(project, model).singleOrNull()
        }

        init {
            data.putUserData(IntelliJDlangNewProjectWizardData.KEY, this)
        }

        private fun setupDlangSdkUI(builder: Panel) {
            builder.row("D SDK") {
                val sdkTypeFilter = { it: SdkTypeId -> it is DlangSdkType }
                sdkComboBox(context, sdkProperty, DLanguage.MODULE_TYPE_ID, sdkTypeFilter)
                    .columns(COLUMNS_MEDIUM)
                    .whenItemSelectedFromUi { logSdkChanged(sdk) }
                    .onApply { logSdkFinished(sdk) }
            }.bottomGap(BottomGap.SMALL)
        }
    }

    private class AssetStep(private val parent: DlangStep) : AssetsNewProjectWizardStep(parent) {
        override fun setupAssets(project: Project) {
            setOutputDirectory(parent.contentRoot)

            if (context.isCreatingNewProject) {
                addAssets(StandardAssetsProvider().getIntelliJIgnoreAssets())
            }
            if (parent.addSampleCode) {
                withDlangSampleCodeAsset(project, "source")
            }
        }
    }
}
