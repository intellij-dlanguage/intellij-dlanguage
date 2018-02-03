package io.github.intellij.dlanguage.messagebus

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile


interface DcdToolChangeListener {
    fun onDcdClientChange()
    fun onDcdServerChange()
}

interface DfmtToolChangeListener {
    fun onDfmtToolChange()
}

interface DscannerToolChangeListener {
    fun onDscannerToolChange()
}

interface GdbToolChangeListener {
    fun onGdbToolChange()
}

interface DubChangeNotifier {

    fun onDubToolChange()

    /**
     * @param project
     * @param module
     * @param dubFile the dub.json or dub.sdl that has been changed
     */
    fun onDubFileChange(project: Project, module: Module, dubFile: VirtualFile)
}
