package io.github.intellij.dub.project

import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.impl.BulkVirtualFileListenerAdapter
import io.github.intellij.dub.project.DubConfigFileListener.Companion.getDubFileFromModule

/**
 * Created by francis on 1/27/2018.
 */
class DubListenerComponent : ProjectActivity {
    override suspend fun execute(project: Project) {
        for (module in ModuleManager.getInstance(project).modules) {
            val dubFile = getDubFileFromModule(module!!)
            if (dubFile != null) {
                project
                    .messageBus
                    .connect()
                    .subscribe(
                        VirtualFileManager.VFS_CHANGES,
                        BulkVirtualFileListenerAdapter(DubConfigFileListener(dubFile, project, module))
                    )
            }
        }
    }
}
