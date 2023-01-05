package io.github.intellij.dub.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.util.text.StringUtil
import io.github.intellij.dlanguage.module.DlangModuleType
import io.github.intellij.dlanguage.settings.ToolKey
import javax.swing.Icon

/**
 * If you intend to use AnAction with dub, use this base class instead. It will ensure that dub is available
 * @author Samael Bate (singingbush)
 */
abstract class DubAction(text: String? = null, description: String? = null, icon: Icon) : DumbAwareAction(text, description, icon) {

    override fun update(e: AnActionEvent) {
        super.update(e)

        e.presentation.isEnabledAndVisible = canUseDub(e)
    }

    private fun canUseDub(e: AnActionEvent) : Boolean {
        val project = AnAction.getEventProject(e) ?: return false
        return StringUtil.isNotEmpty(ToolKey.DUB_KEY.path) && DlangModuleType.findModules(project).isNotEmpty()
    }

}