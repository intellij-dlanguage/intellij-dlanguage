package io.github.intellij.dlanguage.dcd.server

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory

class DCDServerStatusBarWidgetFactory : StatusBarWidgetFactory {

    private companion object {
        const val ID = "DCD Server status"
    }

    override fun getId(): String = ID

    override fun getDisplayName(): String = ID

    /**
     * Returns availability of widget.
     * <p>
     * `False` means that IDE won't try to create a widget or will dispose it on {@link com.intellij.openapi.wm.impl.status.widget.StatusBarWidgetsManager#updateWidget} call.
     * <p>
     * E.g. `false` can be returned for
     * <ul>
     * <li>notifications widget if Event log is shown as a tool window</li>
     * <li>memory indicator widget if it is disabled in the appearance settings</li>
     * <li>git widget if there are no git repos in a project</li>
     * </ul>
     * <p>
     * Whenever availability is changed, you need to call {@link com.intellij.openapi.wm.impl.status.widget.StatusBarWidgetsManager#updateWidget(StatusBarWidgetFactory)}
     * explicitly to get status bar updated.
     */
    override fun isAvailable(project: Project): Boolean {
        return project.isOpen
    }

    override fun createWidget(project: Project): StatusBarWidget {
        return DCDServerStatusBarWidget(project)
    }

    override fun disposeWidget(widget: StatusBarWidget) = widget.dispose()

    /**
     * @return Returns whether the widget can be enabled on the given status bar right now.
     * Status bar's context menu with enable/disable action depends on the result of this method.
     * <p>
     * It's better to have this method aligned with {@link com.intellij.openapi.wm.impl.status.EditorBasedStatusBarPopup.WidgetState#HIDDEN},
     * whenever state is {@code HIDDEN}, this method should return {@code false}.
     * Otherwise, enabling widget via context menu will not have any visual effect.
     * <p>
     * E.g. {@link com.intellij.openapi.wm.impl.status.EditorBasedWidget} are available if editor is opened in a frame that given status bar is attached to
     * <p>
     * For creating editor based widgets see also {@link com.intellij.openapi.wm.impl.status.widget.StatusBarEditorBasedWidgetFactory}
     */
    override fun canBeEnabledOn(statusBar: StatusBar): Boolean {
        return statusBar.project != null
    }
}
