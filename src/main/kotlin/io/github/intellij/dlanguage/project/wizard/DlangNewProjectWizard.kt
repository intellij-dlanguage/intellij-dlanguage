package io.github.intellij.dlanguage.project.wizard

import com.intellij.ide.wizard.AbstractNewProjectWizardMultiStep
import com.intellij.ide.wizard.LanguageNewProjectWizardData
import com.intellij.ide.wizard.LanguageNewProjectWizardData.Companion.languageData
import com.intellij.ide.wizard.NewProjectWizardStep
import com.intellij.ide.wizard.language.LanguageGeneratorNewProjectWizard
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.project.wizard.generator.BuildSystemDlangNewProjectWizard
import io.github.intellij.dlanguage.project.wizard.generator.BuildSystemDlangNewProjectWizardData
import javax.swing.Icon

class DlangNewProjectWizard : LanguageGeneratorNewProjectWizard {

    override val icon: Icon = DLanguage.Icons.LANGUAGE

    override val name = "D"

    override val ordinal = 600

    override fun createStep(parent: NewProjectWizardStep): NewProjectWizardStep = NewDlangProjectStep(parent)

    class NewDlangProjectStep(parent: NewProjectWizardStep) :
        AbstractNewProjectWizardMultiStep<NewDlangProjectStep, BuildSystemDlangNewProjectWizard>(parent, BuildSystemDlangNewProjectWizard.EP_NAME),
        LanguageNewProjectWizardData by parent.languageData!!,
        BuildSystemDlangNewProjectWizardData {

        override val self = this

        override val label = "Build System:" // todo: use DlangBundle message bundle

        override val buildSystemProperty by ::stepProperty

        override var buildSystem by ::step

        init {
            data.putUserData(BuildSystemDlangNewProjectWizardData.KEY, this)
        }

    }

}
