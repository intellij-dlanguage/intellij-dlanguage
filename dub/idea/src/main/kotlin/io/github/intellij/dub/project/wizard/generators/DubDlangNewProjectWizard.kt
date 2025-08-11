package io.github.intellij.dub.project.wizard.generators

import com.intellij.ide.wizard.NewProjectWizardStep
import com.intellij.ide.wizard.NewProjectWizardStep.Companion.ADD_SAMPLE_CODE_PROPERTY_NAME
import com.intellij.openapi.observable.util.bindBooleanStorage
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.SdkTypeId
import com.intellij.openapi.roots.ui.configuration.sdkComboBox
import com.intellij.ui.dsl.builder.BottomGap
import com.intellij.ui.dsl.builder.COLUMNS_MEDIUM
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.columns
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangSdkType
import io.github.intellij.dlanguage.project.wizard.DlangNewProjectWizard
import io.github.intellij.dlanguage.project.wizard.generator.BuildSystemDlangNewProjectWizard
import io.github.intellij.dlanguage.project.wizard.generator.BuildSystemDlangNewProjectWizardData
import io.github.intellij.dub.packageConfig.DubDsl
import io.github.intellij.dub.project.wizard.DubNewProjectWizardStep


internal class DubDlangNewProjectWizard : BuildSystemDlangNewProjectWizard {

    override val name = "Dub"

    override val ordinal = 200

    override fun createStep(parent: DlangNewProjectWizard.NewDlangProjectStep): NewProjectWizardStep =
        DubStep(parent)

    class DubStep(parent: DlangNewProjectWizard.NewDlangProjectStep) :
        DubNewProjectWizardStep<DlangNewProjectWizard.NewDlangProjectStep>(parent),
        BuildSystemDlangNewProjectWizardData by parent,
        DubDlangNewProjectWizardData {

        private val addSampleCodeProperty = propertyGraph.property(true)
            .bindBooleanStorage(ADD_SAMPLE_CODE_PROPERTY_NAME)

        override fun setupUI(builder: Panel) {
            setupDlangSdkUI(builder)
            setupDubFileLang(builder)
            setupDubProjectType(builder)
        }

        override fun setupProject(project: Project) {
            linkDubProject(project)
        }

        private fun setupDlangSdkUI(builder: Panel) {
            builder.row("D SDK") {
                val sdkTypeFilter = { it: SdkTypeId -> it is DlangSdkType }
                sdkComboBox(context, sdkProperty, DLanguage.MODULE_TYPE_ID, sdkTypeFilter)
                    .columns(COLUMNS_MEDIUM)
            }.bottomGap(BottomGap.SMALL)
        }

        private fun setupDubFileLang(builder: Panel) {
            builder.row("Dub DSL:") {
                segmentedButton(DubDsl.entries) {
                    text = when (it) {
                        DubDsl.SDLANG -> "SDLang"
                        DubDsl.JSON -> "JSON"
                    }
                }
                    .bind(dubDslProperty)
            }.bottomGap(BottomGap.SMALL)
        }

        private fun setupDubProjectType(builder: Panel) {
            builder.row("Project Type:") {
                segmentedButton(DubProjectType.entries) { text = it.text }
                    .bind(dubProjectTypeProperty)
            }.bottomGap(BottomGap.SMALL)
        }

        init {
            data.putUserData(DubDlangNewProjectWizardData.KEY, this)
        }
    }
}
