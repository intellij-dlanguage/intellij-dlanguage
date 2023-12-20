package io.github.intellij.dub.project

import com.intellij.ide.GeneralSettings
import com.intellij.ide.impl.ProjectUtil
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ex.ProjectManagerEx
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.projectImport.ProjectImportBuilder
import com.intellij.projectImport.ProjectOpenProcessorBase
import com.intellij.util.ArrayUtil
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangBundle.message
import io.github.intellij.dlanguage.DlangSdkType
import io.github.intellij.dlanguage.module.DlangModuleBuilder
import io.github.intellij.dlanguage.settings.ToolKey
import javax.swing.Icon

/**
 * Used when opening a dub project within the IDE.
 *
 * Some time ago we ended having two ProjectOpenProcessors: this one and CLionDubProjectOpenProcessor.
 * Reason being is that this class was using code from java-impl that would not work in CLion:
 * com.intellij.projectImport.ProjectImportBuilder
 * com.intellij.projectImport.ProjectOpenProcessorBase
 * This should be revisited so that there is a single ProjectOpenProcessor (this one), which works in all
 * Intellij IDEs. Alternatively this could be a base class with both an IDEA specific version and a CLion one.
 */
class DubProjectOpenProcessor : ProjectOpenProcessorBase<DubProjectImportBuilder>() {
    override val name: String
        get() = NAME

    override val icon: Icon
        get() = DLanguage.Icons.FILE

    // from ProjectOpenProcessorBase<DubProjectImportBuilder>
    override val supportedExtensions: Array<String>
        get() = arrayOf("json", "sdl")

    override fun canOpenProject(file: VirtualFile): Boolean = if (file.isDirectory)
            file.children?.any { isProjectFile(it) } ?: false
        else
            isProjectFile(file)

    override fun isProjectFile(file: VirtualFile): Boolean {
        return ArrayUtil.contains(file.name, *SUPPORTED_FILES)
    }

    override fun doOpenProject(
        virtualFile: VirtualFile,
        projectToClose: Project?,
        forceOpenInNewFrame: Boolean
    ): Project? {
        LOG.debug("Opening dub project")

        if (projectToClose != null && !forceOpenInNewFrame) {
            val exitCode = ProjectUtil.confirmOpenOrAttachProject()
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

        val project = ProjectUtil.openOrCreateProject(baseDir.name, baseDir.toNioPath())

        if (project != null) {
            // Run on EDT for with the ability to take a write action lock
            ApplicationManager.getApplication().invokeLater {
                var mm = ModuleManager.getInstance(project)
                mm.modules.forEach{
                    // I couldn't figure out where project was gaining the additional module(s)
                    // so we'll just dispose it and it'll be fine and things should work ok without it
                    mm.disposeModule(it)
                }

                val sdk = DlangSdkType.findOrCreateSdk()
                val builder = DlangModuleBuilder(sdk)

                ApplicationManager.getApplication().runWriteAction {
                    // must be run in a write action
                    ProjectRootManager.getInstance(project).projectSdk = sdk
                };

                // must run on EDT for AWT events (can show dialog) but not in write action
                builder.commit(project)
                ProjectUtil.focusProjectWindow(project, false)
            }
        }
        return project
    }

    // Need to override when extending ProjectOpenProcessorBase<DubProjectImportBuilder>
    override fun doQuickImport(file: VirtualFile, context: WizardContext): Boolean {
        val builder: DubProjectImportBuilder =
            ProjectImportBuilder.EXTENSIONS_POINT_NAME.findExtensionOrFail(
                DubProjectImportBuilder::class.java
            )
        val rootDirectory = file.parent
        return if (StringUtil.isNotEmpty(ToolKey.DUB_KEY.path)) {
            builder.getParameters().dubBinary = ToolKey.DUB_KEY.path
            //builder.setRootDirectory(context.getProjectFileDirectory());
            builder.setRootDirectory(rootDirectory.path)
            context.projectName = rootDirectory.name
            LOG.debug("Opening dub project")
            true
        } else {
            LOG.warn("Couldn't open project as dub not configured")
            Messages.showInfoMessage(message("d.ui.projectopen.missing.dub.binary"), message("d.ui.projectopen.dub"))
            false
        }
    }

    companion object {
        private val LOG = Logger.getInstance(
            DubProjectOpenProcessor::class.java.name
        )
        private const val NAME = "Dub"
        private val SUPPORTED_FILES = arrayOf("dub.json", "dub.sdl")
    }
}
