package io.github.intellij.dub.project

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileEvent
import com.intellij.openapi.vfs.VirtualFileListener
import io.github.intellij.dlanguage.messagebus.Topics
import io.github.intellij.dub.actions.ProcessDLibs.Companion.processDLibs

/**
 * Created by francis on 1/18/2018.
 */
class DubConfigFileListener(
    private val dubConfigFile: VirtualFile,
    private val project: Project,
    private val module: Module
) : VirtualFileListener {
    init {
        processDLibs(project, module, true, false)
        project.messageBus
            .syncPublisher(Topics.DUB_FILE_CHANGE)
            .onDubFileChange(project, module, dubConfigFile)
    }

    /**
     * Fired when the contents of a virtual file is changed.
     *
     * @param event the event object containing information about the change.
     */
    override fun contentsChanged(event: VirtualFileEvent) {
        if (event.file == dubConfigFile) {
            processDLibs(project, module, true, false)
            project.messageBus
                .syncPublisher(Topics.DUB_FILE_CHANGE)
                .onDubFileChange(project, module, dubConfigFile)
        }
    }

    /**
     * Fired before the deletion of a file is processed.
     *
     * @param event the event object containing information about the change.
     */
    override fun beforeFileDeletion(event: VirtualFileEvent) {
        if (event.file == dubConfigFile) {
            Notifications.Bus.notify(
                Notification(
                    "Dub", "Dub",
                    "dub.json or dub.sdl file deleted.",
                    NotificationType.WARNING
                ),
                project
            )
        }
    }

    companion object {
        @JvmStatic
        fun getDubFileFromModule(module: Module): VirtualFile? {
            val projectDir = module.project.guessProjectDir()
            if (projectDir != null && projectDir.children != null) {
                for (file in projectDir.children) {
                    if (file.isValid &&
                        (file.name.equals("dub.json", ignoreCase = true) ||
                                file.name.equals("dub.sdl", ignoreCase = true))
                    ) {
                        return file
                    }
                }
            }
            return null
        }
    }
}
