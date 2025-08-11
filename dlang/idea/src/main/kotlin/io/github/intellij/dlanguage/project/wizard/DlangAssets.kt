package io.github.intellij.dlanguage.project.wizard

import com.intellij.ide.projectWizard.generators.AssetsNewProjectWizardStep
import com.intellij.openapi.project.Project
import java.util.*

private const val DEFAULT_FILE_NAME = "main.d";

private const val DEFAULT_TEMPLATE_WITH_ONBOARDING_TIPS_NAME = "SampleCodeWithOnboardingTips.d"

object DlangAssets {
    fun getDlangSampleTemplateName(): String {
        return DEFAULT_TEMPLATE_WITH_ONBOARDING_TIPS_NAME
    }

    fun getDSampleSourcePath(sourceRootPath: String, filename: String): String {
        val pathJoiner = StringJoiner("/")
        pathJoiner.add(sourceRootPath).add(sourceRootPath)
        pathJoiner.add(filename)
        return pathJoiner.toString()
    }
}

fun AssetsNewProjectWizardStep.withDlangSampleCodeAsset(project: Project,
                                                        sourceRootPath: String,
                                                        filename: String = DEFAULT_FILE_NAME) {
    val currentTemplate = DlangAssets.getDlangSampleTemplateName();
    val sourcePath = DlangAssets.getDSampleSourcePath(sourceRootPath, filename)
    withDlangSampleCodeAsset(sourcePath, currentTemplate)
}

private fun AssetsNewProjectWizardStep.withDlangSampleCodeAsset(
                             sourcePath: String,
                             templateName: String) {

    addTemplateAsset(sourcePath, templateName, mapOf())
    addFilesToOpen(sourcePath)
}
