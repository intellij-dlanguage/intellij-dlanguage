package io.github.intellij.dlanguage.messagebus

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import io.github.intellij.dlanguage.settings.ToolSettings

interface ToolChangeListener {
    /**
     * @param settings the ToolSettings (path and flags) for the D Tool that has been changed
     */
    fun onToolSettingsChanged(settings: ToolSettings)
}


interface DubChangeNotifier {

    /**
     * @param project
     * @param module
     * @param dubFile the dub.json or dub.sdl that has been changed
     */
    fun onDubFileChange(project: Project, module: Module, dubFile: VirtualFile)

    /**
     * @param project
     * @param module
     * @param dubFile the dub.selections.json that has been changed
     */
    fun onDubSelectionsFileChange(project: Project, module: Module, dubFile: VirtualFile)
}
