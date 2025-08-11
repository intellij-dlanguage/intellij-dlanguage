package io.github.intellij.dub.project.wizard.generators

import com.intellij.ide.wizard.NewProjectWizardStep
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.util.Key
import io.github.intellij.dub.project.wizard.DubNewProjectWizardData

interface DubDlangNewProjectWizardData : DubNewProjectWizardData {

    override var sdk: Sdk?

    companion object {
        val KEY = Key.create<DubDlangNewProjectWizardData>(DubDlangNewProjectWizardData::class.java.name)

        @JvmStatic
        val NewProjectWizardStep.dlangDubData: DubDlangNewProjectWizardData?
            get() = data.getUserData(KEY)
    }
}
