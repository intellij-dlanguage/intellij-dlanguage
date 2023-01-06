package io.github.intellij.dub.project

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.projectImport.ProjectImportProvider
import io.github.intellij.dub.module.DubBinaryForModuleStep
import java.util.*

/**
 * IDEA only
 */
class DubProjectImportProvider : ProjectImportProvider(DubProjectImportBuilder()) {
    override fun createSteps(wizardContext: WizardContext): Array<ModuleWizardStep> {
        val setDubBinary: ModuleWizardStep = DubBinaryForModuleStep(wizardContext)
        return arrayOf(setDubBinary)
    }

    override fun canImport(
        fileOrDirectory: VirtualFile,
        project: Project?
    ): Boolean {
        // If we're not importing a directory, validate it as a file.
        if (!fileOrDirectory.isDirectory) return canImportFromFile(fileOrDirectory)

        // check for dub.json
        val dubJson = fileOrDirectory.findChild("dub.json")
        if (dubJson != null) {
            if (canImportFromFile(dubJson)) {
                return true
            }
        }

        // check for dub.sdl
        val dubSdl = fileOrDirectory.findChild("dub.sdl")
        if (dubSdl != null) {
            if (canImportFromFile(dubSdl)) {
                return true
            }
        }

        // alternatively, check all the children for a dub.json or a dub.sdl
        return Arrays.stream(fileOrDirectory.children)
            .filter { f: VirtualFile -> !f.isDirectory }
            .anyMatch { file: VirtualFile -> canImportFromFile(file) }
    }

    public override fun canImportFromFile(file: VirtualFile): Boolean {
        return "dub.json".equals(file.name, ignoreCase = true) || "dub.sdl".equals(file.name, ignoreCase = true)
    }
}
