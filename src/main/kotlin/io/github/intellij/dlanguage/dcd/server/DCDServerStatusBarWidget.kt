package io.github.intellij.dlanguage.dcd.server

import com.intellij.execution.runners.ExecutionUtil
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectLocator
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.ListPopup
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.CustomStatusBarWidget
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.impl.status.EditorBasedStatusBarPopup
import com.intellij.util.concurrency.EdtExecutorService
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.actions.RestartDCD
import io.github.intellij.dlanguage.actions.StopDCDServer
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
        const val WIDGET_TEXT = "DCD Server"
    }

    private val widgetStateIcon = AllIcons.Debugger.ThreadStates.Socket

    private var future: ScheduledFuture<*>? = null

    init {
        this.future = EdtExecutorService
            .getScheduledExecutorInstance()
            .scheduleWithFixedDelay({ this.update() }, 2, 2, TimeUnit.SECONDS)
    }

    override fun ID(): String = ID

    // called on initial creation and whenever it's clicked
    override fun getWidgetState(file: VirtualFile?): WidgetState {
        if (file?.fileType !is DlangFileType) return WidgetState.HIDDEN

        val project = ProjectLocator.getInstance().guessProjectForFile(file)
        project?: return WidgetState.HIDDEN
        val module = ModuleUtil.findModuleForFile(file, this.project)
        module?: return WidgetState.HIDDEN
        val dcdServer: DCDCompletionServer? = module.getService(DCDCompletionServer::class.java)

        val state: WidgetState = when(dcdServer) {
            null -> WidgetState.HIDDEN
            else -> when(dcdServer.isExecutable) {
                true -> when(dcdServer.isRunning) {
                    true -> {
                        val state = WidgetState("DCD Server running", WIDGET_TEXT, true)
                        state.icon = ExecutionUtil.getLiveIndicator(widgetStateIcon, true)
//                        state.icon = ExecutionUtil.getLiveIndicator(AnimatedIcon.Blinking(widgetStateIcon), true)
//                        state.icon = ExecutionUtil.getLiveIndicator(IconWithDaemonOverlay(widgetStateIcon), true)
                        state
                    }
                    false -> {
                        val state = WidgetState("Restart DCD Server", WIDGET_TEXT, true)
                        state.icon = ExecutionUtil.getLiveIndicator(widgetStateIcon, false)
                        state
                    }
                }
                false -> WidgetState.HIDDEN // DCD is not configured
            }
        }

        return state
    }

    /*
    * todo: Register to topics such as DCD_SERVER_TOOL_CHANGE and take appropriate action
    */
//    @Deprecated("Use registerCustomListeners(MessageBusConnection)")
//    override fun registerCustomListeners() {
//        val connection = ApplicationManager.getApplication().messageBus.connect(this)
//
//        connection.subscribe(DCD_SERVER_TOOL_CHANGE)
//    }

    override fun createInstance(project: Project): StatusBarWidget = DCDServerStatusBarWidget(project)

    override fun createPopup(context: DataContext): ListPopup {
        val group = DefaultActionGroup.createPopupGroupWithEmptyText()

        ActionManager.getInstance().let {
            group.add(it.getAction(RestartDCD.ID))
            group.add(it.getAction(StopDCDServer.ID))
        }

        return JBPopupFactory.getInstance()
            .createActionGroupPopup("DCD Actions", group, context, JBPopupFactory.ActionSelectionAid.SPEEDSEARCH, true)
    }

    override fun dispose() {
        this.future?.cancel(true)
        this.future = null

        super<CustomStatusBarWidget>.dispose()
    }
}
