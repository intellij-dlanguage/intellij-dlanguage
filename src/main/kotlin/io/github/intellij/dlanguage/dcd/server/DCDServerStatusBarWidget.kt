package io.github.intellij.dlanguage.dcd.server

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.ListPopup
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.CustomStatusBarWidget
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.impl.status.EditorBasedStatusBarPopup
import com.intellij.ui.AnimatedIcon
import com.intellij.util.concurrency.EdtExecutorService
import io.github.intellij.dlanguage.actions.RestartDCD
import io.github.intellij.dlanguage.codeinsight.dcd.DCDCompletionServer
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

// look at other status bar widgets like:
//   com.intellij.openapi.wm.impl.status.MemoryUsagePanel
//   com.intellij.openapi.wm.impl.status.EncodingPanel
//   com.intellij.diagnostic.IdeMessagePanel
// for examples of how to implement this type of UI feature
class DCDServerStatusBarWidget(project: Project) : EditorBasedStatusBarPopup(project, false), CustomStatusBarWidget {

    private companion object {
        const val ID = "DCD Server Status Bar Widget"
        val log: Logger = Logger.getInstance(DCDServerStatusBarWidget::class.java)
    }

    private var future: ScheduledFuture<*>? = null

    init {
        this.future = EdtExecutorService
            .getScheduledExecutorInstance()
            .scheduleWithFixedDelay({ this.update() }, 2, 2, TimeUnit.SECONDS)
    }

    override fun ID(): String = ID

    // called on initial creation and whenever it's clicked
    override fun getWidgetState(file: VirtualFile?): WidgetState {
        val dcdServer: DCDCompletionServer? = DCDCompletionServer.getInstance() // can't use project.getComponent(DCDCompletionServer::class.java)

        val state: WidgetState = when(dcdServer) {
            null -> WidgetState.HIDDEN
            else -> return when(dcdServer.isRunning) {
                true -> {
                    val state = WidgetState("DCD Server running", "DCD Server", false)
                    state.icon = AllIcons.Debugger.ThreadStates.Socket
                    state
                }
                false -> {
                    val state = WidgetState("Restart DCD Server", "DCD Server", true)
                    state.icon = AnimatedIcon.Blinking(AllIcons.General.Warning) // AllIcons.Ide.FatalError
                    state
                }
            }
        }

        return state
    }

    override fun registerCustomListeners() {
        // do nothing
    }

    override fun createInstance(project: Project): StatusBarWidget = DCDServerStatusBarWidget(project!!)

    override fun createPopup(context: DataContext?): ListPopup? {
        val group = DefaultActionGroup.createPopupGroupWithEmptyText()

        group.add(ActionManager.getInstance().getAction(RestartDCD.ID))

        return JBPopupFactory.getInstance()
            .createActionGroupPopup("DCD Actions", group, context!!, JBPopupFactory.ActionSelectionAid.SPEEDSEARCH, true)
    }

    override fun dispose() {
        this.future?.cancel(true)
        this.future = null

        super.dispose()
    }
}
