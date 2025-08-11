package io.github.intellij.dlanguage.project.wizard.generator

import com.intellij.ide.wizard.BuildSystemNewProjectWizardData
import com.intellij.ide.wizard.NewProjectWizardStep
import com.intellij.openapi.util.Key

interface BuildSystemDlangNewProjectWizardData : BuildSystemNewProjectWizardData {

    companion object {
        val KEY = Key.create<BuildSystemDlangNewProjectWizardData>(BuildSystemDlangNewProjectWizardData::javaClass.name)

        @JvmStatic
        val NewProjectWizardStep.dlangBuildSystemData: BuildSystemDlangNewProjectWizardData?
            get() = data.getUserData(KEY)

    }
}
