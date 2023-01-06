package io.github.intellij.dub.project

import com.intellij.ide.GeneralSettings
import com.intellij.ide.impl.OpenProjectTask
import com.intellij.ide.impl.ProjectUtil
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ex.ProjectManagerEx
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.projectImport.ProjectOpenProcessor
import com.intellij.util.ArrayUtil
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangSdkType
import io.github.intellij.dlanguage.module.DlangModuleBuilder
import javax.swing.Icon

/**
 * Used when opening a dub project within a non-Java IDE
 */
class CLionDubProjectOpenProcessor : ProjectOpenProcessor() {
    override fun getIcon(): Icon {
        return DLanguage.Icons.FILE
    }

    override fun getName(): String {
        return NAME
    }

    override fun canOpenProject(file: VirtualFile): Boolean {
        return findSupportedFile(file) != null
    }

    private fun findSupportedFile(file: VirtualFile): VirtualFile? {
        return if (file.isDirectory) {
            val children = file.children
            if (children != null) {
                for (child in children) {
                    if (ArrayUtil.contains(child.name, *SUPPORTED_FILES)) return child
                }
            }
            null
        } else {
            if (ArrayUtil.contains(file.name, *SUPPORTED_FILES)) file else null
        }
    }

    override fun doOpenProject(
        virtualFile: VirtualFile,
        projectToClose: Project?,
        forceOpenInNewFrame: Boolean
    ): Project? {
        if (projectToClose != null && !forceOpenInNewFrame) {
            val exitCode = ProjectUtil.confirmOpenNewProject(false)
            if (exitCode == GeneralSettings.OPEN_PROJECT_SAME_WINDOW) {
                if (!ProjectManagerEx.getInstanceEx().closeAndDispose(projectToClose)) {
                    return null
                }
            } else if (exitCode != GeneralSettings.OPEN_PROJECT_NEW_WINDOW) {
                // not in a new window
                return null
            }
        }
        val baseDir = if (virtualFile.isDirectory) virtualFile else virtualFile.parent
        val project = ProjectManagerEx.getInstanceEx()
            .newProject(baseDir.toNioPath(), OpenProjectTask.build().withProjectName(baseDir.name))
        if (project != null) {
            WriteAction.run<RuntimeException> {
                val sdk = DlangSdkType.findOrCreateSdk()
                ProjectRootManager.getInstance(project).projectSdk = sdk
                val builder = DlangModuleBuilder()
                builder.moduleJdk = sdk
                builder.commit(project)
            }
            ProjectManagerEx.getInstanceEx().openProject(project)
        }
        return project
    }

    companion object {
        const val NAME = "Dub"
        val SUPPORTED_FILES = arrayOf("dub.json", "dub.sdl")
    }
}
