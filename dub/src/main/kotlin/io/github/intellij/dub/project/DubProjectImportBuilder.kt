package io.github.intellij.dub.project

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.module.ModifiableModuleModel
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.module.ModuleWithNameAlreadyExists
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.util.InvalidDataException
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.packaging.artifacts.ModifiableArtifactModel
import com.intellij.projectImport.ProjectImportBuilder
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangSdkType
import io.github.intellij.dlanguage.settings.ToolKey
import io.github.intellij.dlanguage.utils.DToolsNotificationAction
import io.github.intellij.dub.module.DlangDubModuleBuilder
import io.github.intellij.dub.project.DubConfigFileListener.Companion.getDubFileFromModule
import org.jdom.JDOMException
import java.io.IOException
import java.util.function.Consumer
import javax.swing.Icon

class DubProjectImportBuilder : ProjectImportBuilder<DubPackage>() {
    private var parameters: Parameters? = null
    fun setRootDirectory(path: String?) {}
    fun getParameters(): Parameters {
        if (parameters == null) {
            parameters = Parameters()
        }
        return parameters!!
    }

    override fun getName(): String {
        return CLionDubProjectOpenProcessor.NAME
    }

    override fun getIcon(): Icon {
        return DLanguage.Icons.MODULE
    }

    override fun getList(): List<DubPackage>? {
        return getParameters().packages
    }

    override fun setList(list: List<DubPackage>) {
        getParameters().packages = list
    }

    override fun setOpenProjectSettingsAfter(on: Boolean) {
    }

    override fun isMarked(dubPackage: DubPackage): Boolean {
        return list!!.contains(dubPackage)
    }

    override fun commit(
        project: Project,
        modifiableModuleModel: ModifiableModuleModel?,
        modulesProvider: ModulesProvider?,
        modifiableArtifactModel: ModifiableArtifactModel?
    ): List<Module> {
        val model = modifiableModuleModel ?: ModuleManager.getInstance(project).getModifiableModel()
        val modules: MutableList<Module> = ArrayList()
        ApplicationManager.getApplication().runWriteAction {
            commitSdk(project)
            modules.addAll(buildModules(project, model))
        }
        return modules
    }

    private fun findDubConfigFile(module: Module): VirtualFile? {
        val file = getDubFileFromModule(module)
        if (file != null) {
            return file
        }
        Notifications.Bus.notify(
            Notification(
                "Dub Import", "Dub Import",
                "Dub project does not seem to contain dub.json or dub.sdl.",
                NotificationType.WARNING
            ),
            module.project
        )
        return null
    }

    private fun buildModules(
        project: Project,
        moduleModel: ModifiableModuleModel
    ): List<Module> {
        val moduleList: MutableList<Module> = ArrayList()
        if (ToolKey.DUB_KEY.path.isNullOrEmpty()) {
            Notifications.Bus.notify(
                Notification(
                    "Dub Import", "Dub Import",
                    "DUB executable path is empty",
                    NotificationType.WARNING
                )
                    .addAction(DToolsNotificationAction("Configure")),
                project
            )
            // prevent a crash, inform callers that we can't actually call dub
            // not ideal, should really be handled elsewhere
            throw ConfigurationException("Missing dub, cannot import")
        }
        val dubConfigurationParser = DubConfigurationParser(
            project,
            ToolKey.DUB_KEY.path!!,
            false,
            this.getFileToImport()
        )
        val dubProject = dubConfigurationParser.dubProject
        dubProject.ifPresent { (_, pkg): DubProject ->
            val builder = DlangDubModuleBuilder()
            builder.moduleFilePath = pkg.path + pkg.name + ".iml"
            builder.contentEntryPath = pkg.path
            builder.name = pkg.name
            pkg.sourcesDirs.forEach(Consumer { dir: String -> builder.addSourcePath(pkg.path + dir) })
            try {
                val module = builder.createModule(moduleModel)
                //                DubConfigFileListener
//                    .addProcessDLibsListener(findDubConfigFile(module), project, module);
                builder.commit(project)
                moduleList.add(module)
            } catch (e: InvalidDataException) {
                LOG.error(e)
            } catch (e: IOException) {
                LOG.error(e)
            } catch (e: JDOMException) {
                LOG.error(e)
            } catch (e: ModuleWithNameAlreadyExists) {
                LOG.error(e)
            } catch (e: ConfigurationException) {
                LOG.error(e)
            }
        }
        return moduleList
    }

    private fun commitSdk(project: Project) {
        ProjectRootManager.getInstance(project).projectSdk = DlangSdkType.findOrCreateSdk()
    }

    class Parameters {
        var packages: List<DubPackage>? = null
        @JvmField
        var dubBinary: String? = null
    }

    companion object {
        private val LOG = Logger.getInstance(
            DubProjectImportBuilder::class.java
        )
    }
}
