package io.github.intellij.dlanguage.actions

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessAdapter
import com.intellij.execution.process.ProcessEvent
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.progress.impl.BackgroundableProcessIndicator
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.roots.LibraryOrderEntry
import com.intellij.openapi.roots.ModuleRootModificationUtil
import com.intellij.openapi.roots.OrderRootType
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.ui.components.JBList
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.library.DlangLibraryType
import io.github.intellij.dlanguage.module.DlangModuleType
import io.github.intellij.dlanguage.project.DubConfigurationParser
import io.github.intellij.dlanguage.project.DubPackage
import io.github.intellij.dlanguage.settings.ToolKey
import io.github.intellij.dlanguage.utils.DToolsNotificationListener

/**
 * ported from Java on 06/02/18
 *
 * todo: this class needs a rethink. It's currently tied to the user having dub configured. Also, the Action shouldn't
 * need to be called manually. For dub projects we should scan dub.json/sdl for changes or when opening/importing
 * a project (The same way that the Maven/Gradle integration works) and when not using dub we should just
 * detect when the user adds/removes a library.
 */
class ProcessDLibs : AnAction("Process D Libraries", "Processes the D Libraries", DLanguage.Icons.SDK), DumbAware {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        if (project == null) {
            displayError(e, "Unable to process D libraries - No active project.")
            return
        }
        val modules = DlangModuleType.findModules(project)

        when (modules.size) {
            //0 -> displayError(e, "Unable to process D libraries - No DLanguage modules are used in this project.")
            1 -> processDLibs(project, modules.iterator().next())
            else -> showModuleChoicePopup(e, project, modules)
        }
    }

    /*
    * This method is called repeatedly in UI thread and needs to complete fast. Don't perform any long-running code!
    */
    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = canRunDub(e)
    }

    companion object {
        private val LOG = Logger.getInstance(ProcessDLibs::class.java)
        private val MENU_PATH = "Tools > Process D Libraries"
        private val NOTIFICATION_GROUPID = "Process D Libs"
        private var dubPathAlreadWarned = false

        /**
         * This method is used from various places, when re-working the way libs are processed this should be marked
         * as deprecated and deleted in a future release
         */
        @JvmStatic
        fun processDLibs(project: Project, module: Module, mostlySilentMode: Boolean, buildBefore: Boolean) {
            ApplicationManager.getApplication().runWriteAction {
                // remove all existing libs from module (removing from module doesn't remove from project):
                removeAllLibrariesFromModule(module)

                removeAllLibrariesFromProject(project)

                // todo: perhaps we can use the custom library tables. Need to find out how they work
                // val libs = LibraryTablesRegistrar.getInstance().customLibraryTables
                // LibraryTablesRegistrar.getInstance().customLibraryTables.clear()

                // used to use the removeDLibs method
                // removeDLibs(module, project); // this is not necessary since intellij filters out duplicate libraries.
            }

            ApplicationManager.getApplication().invokeAndWait({
                val task = object : Task.Backgroundable(project,
                    "Updating Dub Libraries") {
                    override fun run(indicator: ProgressIndicator) {
                        processDLibsImpl(project, module, mostlySilentMode, buildBefore)
                    }
                }
                ProgressManager.getInstance()
                    .runProcessWithProgressAsynchronously(task, BackgroundableProcessIndicator(task))
            }, ModalityState.defaultModalityState())
        }

        private fun processDLibsImpl(project: Project, module: Module,
                                     mostlySilentMode: Boolean, buildBefore: Boolean) {
            //todo needs build/fetch before adding libs, also needs to keep track of libs

            // ask dub for required libs
            val dubPath = ToolKey.DUB_KEY.path

            //final String groupId = e.getPresentation().getText();
            if (dubPath == null) {
                if (!this.dubPathAlreadWarned) {
                    val notification = Notification(
                        NOTIFICATION_GROUPID, "Process D Libraries",
                        "DUB executable path is empty<br/><a href='configureDLanguageTools'>Configure</a>",
                        NotificationType.WARNING
                    )
                    notification.setListener(DToolsNotificationListener(project))

                    Notifications.Bus.notify(notification, project)
                    this.dubPathAlreadWarned = true
                }
                return
            }

            val dubParser = DubConfigurationParser(project, dubPath,
                mostlySilentMode)
            if (dubParser.canUseDub()) {
                val dependencies = dubParser.dubPackageDependencies
                for (pkg in dependencies) {
                    val fullName = pkg.name + "-" + pkg.version
                    createLibraryDependency(module, project, fullName, pkg)
                }
            } else {
                LOG.info("not possible to run 'dub describe'")
            }

            if (!mostlySilentMode) {
                val notification = Notification(
                    NOTIFICATION_GROUPID, "Process D Libraries",
                    "Added your dub dependency libraries",
                    NotificationType.INFORMATION
                )
                notification.setListener(DToolsNotificationListener(project))

                Notifications.Bus.notify(notification, project)
            }
        }

        /*
        * perform some basic checks to see if dub can be run. There should be an active project that contains either
        * a dub.json or dub.sdl file and the path should be set for the dub binary.
        */
        private fun canRunDub(e: AnActionEvent): Boolean {
            val project = AnAction.getEventProject(e) ?: return false
            ToolKey.DUB_KEY.path?.isNotBlank() ?: return false
            val virtualRoot = project.guessProjectDir() ?: return false
            return virtualRoot.findChild("dub.json")?.exists() ?:
                            virtualRoot.findChild("dub.sdl")?.exists() ?:
                            false
        }

        private fun showModuleChoicePopup(e: AnActionEvent, project: Project, modules: Collection<Module>) {
            val list = JBList(JBList.createDefaultListModel<Module>(*modules.toTypedArray()))
            val popup = JBPopupFactory.getInstance()
                .createListPopupBuilder(list)
                .setTitle("Process D libraries for module")
                .setItemChoosenCallback { processDLibs(AnAction.getEventProject(e), list.selectedValue as Module) }
                .createPopup()
            popup.showCenteredInCurrentWindow(project)
        }

        private fun processDLibs(project: Project?, module: Module) {
            if (project == null) {
                LOG.warn("Unable to process D libraries - No active D project.")
                //displayError(project, "Unable to process D libraries - No active D project.");
                return
            }
            processDLibs(project, module, false, false)
        }

        private fun createLibraryDependency(module: Module, project: Project,
                                            libraryName: String, dubPackage: DubPackage) {
            getSourcesVirtualFile(dubPackage)?.let { sources ->
                val projectLibraryTable = LibraryTablesRegistrar.getInstance().getLibraryTable(project)
                val projectLibraryModel = projectLibraryTable.modifiableModel

                //val uniqueLibName = LibraryEditingUtil.suggestNewLibraryName(projectLibraryModel, libraryName) // not needed as we remove clashes
                val library = projectLibraryModel.createLibrary(libraryName, DlangLibraryType.DLANG_LIBRARY) // todo: use the ProjectModelExternalSource arg

                val libraryModel = library.modifiableModel

                libraryModel.addRoot(sources, OrderRootType.CLASSES) // todo: Use our own OrderRootType, such as LibFileRootType.getInstance()

                //todo add binary libs/dub.json as well
                ApplicationManager.getApplication()
                    .invokeAndWait {
                        ApplicationManager.getApplication().runWriteAction {
                            libraryModel.commit()
                            projectLibraryModel.commit()
                            for (projectModule in ModuleManager.getInstance(project).modules) {
                                // todo: should we be doing ModuleRootModificationUtil.addModuleLibrary()
                                ModuleRootModificationUtil.addDependency(projectModule, library)
                                LOG.debug("added dependency ${library.presentableName} to module '${projectModule.name}'")
                            }

                        }
                    }

            }
        }

        // todo: This method needs breaking down. Also, a dub package can have multiple source directories
        private fun getSourcesVirtualFile(dubPackage: DubPackage): VirtualFile? {
            // for now I'm putting this hideous kludge in which makes it work they way it always has
            val srcDir = if(dubPackage.sourcesDirs.isNotEmpty()) {
                if(dubPackage.sourcesDirs.size > 1) {
                    LOG.warn("it's likely that we're not processing ${dubPackage.name} correctly. ${dubPackage.sourcesDirs.size} source folders found")
                }
                dubPackage.sourcesDirs[0]
            } else "source"

            val sourcesPathUrl = if (SystemInfo.isWindows) {
                VirtualFileManager.constructUrl(LocalFileSystem.PROTOCOL, dubPackage.path + srcDir.replace("/", "\\"))
            } else {
                VirtualFileManager.constructUrl(LocalFileSystem.PROTOCOL, dubPackage.path + srcDir)
            }

            return VirtualFileManager.getInstance()
                .refreshAndFindFileByUrl(sourcesPathUrl) ?: return this.dubFetch(dubPackage, sourcesPathUrl)
        }

        private fun dubFetch(dubPackage: DubPackage, sourcesPathUrl: String): VirtualFile? {
            LOG.info("sources not found, fetching them")
            val commandLine = GeneralCommandLine()
            val dubBinaryPath = ToolKey.DUB_KEY.path
            if (dubBinaryPath == null) {
                LOG.error("dub executable should be configured")
                return null
            }
            commandLine.exePath = dubBinaryPath
            val parametersList = commandLine.parametersList
            parametersList.addParametersString("fetch")
            // todo: add some logic here to choose between "dub fetch <package> --version=x.y.z" and "dub fetch <package>@x.y.z"
            // see: https://github.com/dlang/dub/issues/2028
            // if dub is version 1.23 or over:
            parametersList.addParametersString("${dubPackage.name}@${dubPackage.version}")
            // else use old syntax:
            // parametersList.addParametersString(dubPackage.name)
            // parametersList.addParametersString("--version=${dubPackage.version}")

            val dubCommand = commandLine.commandLineString
            val process: OSProcessHandler
            try {
                process = OSProcessHandler(commandLine.createProcess(), dubCommand)
            } catch (e: ExecutionException) {
                LOG.error(e)
                return null
            }

            val builder = StringBuilder()
            val errors = ArrayList<String>()

            process.addProcessListener(object : ProcessAdapter() {
                override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {
                    when (outputType.toString()) {
                        "stdout" -> builder.append(event.text)
                        "stderr" -> errors.add(event.text)
                    }
                }
            })

            process.startNotify()
            process.waitFor()

            LOG.debug("output from fetching package: $builder")

            errors.forEach {
                LOG.warn("Error when running '${dubCommand}' : $it")
            }

            val exitCode = process.exitCode
            if (exitCode != null && exitCode == 0) {
                //try loading again
                return VirtualFileManager.getInstance().refreshAndFindFileByUrl(sourcesPathUrl)
            } else {
                LOG.error("exitcode was no zero for fetching package '${dubPackage.name}' version '${dubPackage.version}'")
            }
            return null
        }

        private fun removeAllLibrariesFromModule(module: Module) {
            ApplicationManager.getApplication().assertWriteAccessAllowed()

            LOG.debug("Clearing libraries from module '${module.name}'")

            ModuleRootModificationUtil.updateModel(module) { model ->
                model.orderEntries
                    .filterIsInstance<LibraryOrderEntry>()
                    .forEach { lib ->
                        model.removeOrderEntry(lib)
                        LOG.debug("removed ${lib.presentableName} from from module '${module.name}'")
                    }
            }
        }

        private fun removeAllLibrariesFromProject(project: Project) {
            ApplicationManager.getApplication().assertWriteAccessAllowed()

            LOG.debug("Clearing libraries from project '${project.name}'")

            LibraryTablesRegistrar.getInstance()
                .getLibraryTableByLevel(LibraryTablesRegistrar.PROJECT_LEVEL, project)?.let { libraryTable ->
                    libraryTable.modifiableModel.libraries.iterator().forEach { lib ->
                        libraryTable.removeLibrary(lib)
                        LOG.debug("removed ${lib.presentableName} from from project '${project.name}'")
                    }
                }
        }

//    private fun removeDLibs(module: Module, project: Project) {
//        val projectLibraryTable = LibraryTablesRegistrar.getInstance()
//            .getLibraryTable(project)
//        for (lib in projectLibraryTable.libraries) {
//            removeLibraryIfNeeded(module, lib.name)
//        }
//    }

//        private fun removeLibraryIfNeeded(module: Module, libraryName: String?) {
//            ApplicationManager.getApplication().assertIsDispatchThread()
//
//            val modelsProvider = ModifiableModelsProvider.SERVICE.getInstance()
//            val model = modelsProvider.getModuleModifiableModel(module)
//            val dLibraryEntry = OrderEntryUtil.findLibraryOrderEntry(model, libraryName!!)
//
//            if (dLibraryEntry != null) {
//                ApplicationManager.getApplication().runWriteAction {
//                    val library = dLibraryEntry.library
//                    if (library != null) {
//                        val table = library.table
//                        if (table != null) {
//                            table.removeLibrary(library)
//                            model.removeOrderEntry(dLibraryEntry)
//                            modelsProvider.commitModuleModifiableModel(model)
//                        }
//                    } else {
//                        modelsProvider.disposeModuleModifiableModel(model)
//                    }
//                }
//            } else {
//                ApplicationManager.getApplication()
//                    .runWriteAction { modelsProvider.disposeModuleModifiableModel(model) }
//            }
//        }

        private fun displayError(e: AnActionEvent, message: String) {
            displayError(AnAction.getEventProject(e), message)
        }

        private fun displayError(project: Project?, message: String) {
            //final String groupId = e.getPresentation().getText();
            Notifications.Bus.notify(
                Notification(NOTIFICATION_GROUPID, "Process D libs", message, NotificationType.ERROR),
                project
            )
            LOG.warn(message)
        }
    }

}
