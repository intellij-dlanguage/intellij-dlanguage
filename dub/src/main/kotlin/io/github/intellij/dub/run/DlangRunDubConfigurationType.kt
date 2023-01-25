package io.github.intellij.dub.run

import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.configurations.ConfigurationTypeBase
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.io.FileUtil
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangBundle.message
import io.github.intellij.dlanguage.run.DlangRunConfigurationFactory

class DlangRunDubConfigurationType : ConfigurationTypeBase(
    "DlangRunDubConfigType",
    message("run.dub.text"),
    message("run.dub.descr"),
    DLanguage.Icons.RUN
) {
    init {
        addFactory(DLanguageFactory(this))
    }

    private class DLanguageFactory(type: ConfigurationType?) : DlangRunConfigurationFactory(type!!) {
        override fun createTemplateConfiguration(project: Project): RunConfiguration {
            return DlangRunDubConfiguration("DlangRunDubConfig", project, this)
        }

        override fun isApplicable(project: Project): Boolean {
            val file = FileUtil.findFileInProvidedPath(project.basePath!!, "dub.json", "dub.sdl")
            return !file.isNullOrEmpty()
        }
    }
}
