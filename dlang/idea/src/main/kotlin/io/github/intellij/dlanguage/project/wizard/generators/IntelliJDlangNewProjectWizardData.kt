package io.github.intellij.dlanguage.project.wizard.generators

import com.intellij.ide.projectWizard.generators.IntelliJNewProjectWizardData
import com.intellij.ide.wizard.NewProjectWizardStep
import com.intellij.openapi.util.Key

interface IntelliJDlangNewProjectWizardData : IntelliJNewProjectWizardData {
    companion object {
        val KEY = Key.create<IntelliJDlangNewProjectWizardData>(IntelliJDlangNewProjectWizardData::class.java.name)

        @JvmStatic
        val NewProjectWizardStep.dlangData: IntelliJDlangNewProjectWizardData?
            get() = data.getUserData(KEY)
    }
}
