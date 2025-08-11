package io.github.intellij.dub.project.wizard

import com.intellij.ide.wizard.AbstractNewProjectWizardStep
import com.intellij.ide.wizard.NewProjectWizardBaseData
import com.intellij.ide.wizard.NewProjectWizardStep
import com.intellij.openapi.module.Module
import com.intellij.openapi.observable.util.bindEnumStorage
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.Sdk
import io.github.intellij.dub.module.DlangDubModuleBuilder
import io.github.intellij.dub.packageConfig.DubDsl
import org.jetbrains.annotations.Nls

abstract class DubNewProjectWizardStep<ParentStep>(
    protected val parentStep: ParentStep
) : AbstractNewProjectWizardStep(parentStep),
    DubNewProjectWizardData
    where ParentStep : NewProjectWizardStep,
          ParentStep : NewProjectWizardBaseData {

    final override val sdkProperty = propertyGraph.property<Sdk?>(null)
    final override val dubDslProperty = propertyGraph.property(DubDsl.JSON)
        .bindEnumStorage("NewProjectWizard.dubDslState")
    final override val dubProjectTypeProperty = propertyGraph.property(DubProjectType.MINIMAL)
        .bindEnumStorage("NewProjectWizard.dubProjectTypeState")

    final override var sdk by sdkProperty
    final override var dubProjectType by dubProjectTypeProperty
    final override var dubDsl by dubDslProperty

    protected fun linkDubProject(
        project: Project,
        builder: DlangDubModuleBuilder = DlangDubModuleBuilder(),
    ): Module? {
        builder.moduleJdk = sdk
        builder.name = parentStep.name
        builder.contentEntryPath = "${parentStep.path}/${parentStep.name}"
        val options: MutableMap<String, String> = HashMap(3)
        options["dubFormat"] = when (dubDsl) {
            DubDsl.SDLANG -> "sdl"
            DubDsl.JSON -> "json"
        }
        options["dubType"] = dubProjectType.text
        builder.setDubOptions(options)

        val model = context.getUserData(NewProjectWizardStep.MODIFIABLE_MODULE_MODEL_KEY)
        return builder.commit(project, model).firstOrNull()
    }

    enum class DubProjectType(val text: @Nls String) {
        MINIMAL("minimal"),
        VIBE("vibe.d"),
        DEIMOS("deimos")
    }
}
