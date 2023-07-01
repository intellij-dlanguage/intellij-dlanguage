package io.github.intellij.dub.actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import io.github.intellij.dlanguage.settings.DLanguageToolsConfigurable

/**
 * @author Samael Bate (singingbush)
 * created on 01/04/2017.
 */
class ConfigureDToolsAction : DubAction("Configure D Tools", null, AllIcons.General.GearPlain) {

    private val LOG: Logger = Logger.getInstance(ConfigureDToolsAction::class.java)

    override fun actionPerformed(e: AnActionEvent) {
        LOG.info("dub settings action performed")

        val project: Project? = DumbAwareAction.getEventProject(e) // equivalent to: CommonDataKeys.PROJECT.getData(e.dataContext)

        project?.let {
            ShowSettingsUtil.getInstance()
                    .showSettingsDialog(project, DLanguageToolsConfigurable::class.java)
        }
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT;
    }
}
