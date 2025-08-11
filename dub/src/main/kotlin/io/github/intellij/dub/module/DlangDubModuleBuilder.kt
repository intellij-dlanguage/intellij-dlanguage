package io.github.intellij.dub.module

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.configurations.ModuleBasedConfiguration
import com.intellij.execution.configurations.ParametersList
import com.intellij.execution.impl.RunManagerImpl
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessAdapter
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessOutputTypes
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.module.ModifiableModuleModel
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleWithNameAlreadyExists
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.util.InvalidDataException
import com.intellij.openapi.util.Key
import io.github.intellij.dlanguage.DlangBundle.message
import io.github.intellij.dlanguage.module.DlangModuleBuilder
import io.github.intellij.dub.run.DlangRunDubConfigurationType
import io.github.intellij.dub.service.DubBinaryPathProvider
import org.jdom.JDOMException
import org.jetbrains.annotations.NonNls
import java.io.File
import java.io.IOException
import java.time.LocalTime
import java.util.concurrent.atomic.AtomicBoolean

class DlangDubModuleBuilder :
    DlangModuleBuilder(BUILDER_ID, message("module.dub.title"), message("module.dub.description")) {
    private var sourcePaths: MutableList<String>? = null
    private var dubOptions: Map<String, String> = HashMap()

    /*
    * When creating a project the following are called in order:
    *   createProject()
    *   commitModule()
    *   createAndCommitIfNeeded()
    *   setupRootModel()
    *   getSourcePaths()
    *
    * The code for running "dub init" has been moved here so that it has access to the Project and the "source"
    * directory should be created prior to the call to getSourcePaths().
    */
    @Throws(
        InvalidDataException::class,
        ConfigurationException::class,
        IOException::class,
        JDOMException::class,
        ModuleWithNameAlreadyExists::class
    )
    override fun createAndCommitIfNeeded(
        project: Project,
        model: ModifiableModuleModel?,
        runFromProjectWizard: Boolean
    ): Module {
        if (!dubOptions.isEmpty() && DubBinaryPathProvider.isDubAvailable()) {
            try {
                val cmd = createDubInitCommand(project.basePath)
                ApplicationManager.getApplication()
                    .executeOnPooledThread { runDubCommand(project, cmd) }
            } catch (e: Exception) {
                LOG.error("dub init failed!", e)
            }
        }
        return super.createAndCommitIfNeeded(project, model, runFromProjectWizard)
    }

    @Throws(ConfigurationException::class)
    override fun setupRootModel(rootModel: ModifiableRootModel) {
        super.setupRootModel(rootModel)
        LOG.debug("Setting up dub-specific root model for Dlang project")
        val project = rootModel.project
        val runManager = RunManagerImpl.getInstanceImpl(project)

        //Create "Run dub" configuration
        val runDubSettings = runManager.findConfigurationByName(RUN_DUB_CONFIG_NAME)
        if (runDubSettings == null) {
            val configurationType = ConfigurationType.CONFIGURATION_TYPE_EP.findExtensionOrFail(
                DlangRunDubConfigurationType::class.java
            )
            val factory = configurationType.configurationFactories[0]
            val settings = runManager.createConfiguration(RUN_DUB_CONFIG_NAME, factory)
            (settings.configuration as ModuleBasedConfiguration<*, *>).setModule(rootModel.module)
            settings.storeInLocalWorkspace()
            runManager.addConfiguration(settings)
            LOG.debug(String.format("Run Configuration added for '%s'", RUN_DUB_CONFIG_NAME))
        }
    }

    /*
     * By default, dub init will create a directory named "source" so this method presumptuously
     * returns "{WORKING_DIR}/source" as the source path. This could result in errors if dub
     * where to start creating files using an alternative directory name.
     */
    override fun getSourcePaths(): List<String>? {
        if (sourcePaths.isNullOrEmpty()) {
            val path: @NonNls String = contentEntryPath + File.separator + "source"
            sourcePaths = arrayListOf(path) // may need to add additional source paths later
        }
        return sourcePaths
    }

    fun addSourcePath(sourcePathInfo: String) {
        if (sourcePaths == null) {
            sourcePaths = ArrayList()
        }
        sourcePaths!!.add(sourcePathInfo)
    }

    fun setDubOptions(options: Map<String, String>) {
        dubOptions = options
    }

    /*
    * Build up the "dub init" command based on the parameters defined during project creation
    */
    private fun createDubInitCommand(workingDirectory: String?): GeneralCommandLine {
        val params = ParametersList()
        params.addParametersString("init")
        params.addParametersString("-n")
        if ((dubOptions["dubParams"] ?: "").isEmpty()) {
            params.addParametersString("--format")
            params.addParametersString(dubOptions["dubFormat"] ?: "json")
            params.addParametersString("--type")
            params.addParametersString(dubOptions["dubType"] ?: "minimal")
        } else {
            params.addParametersString(dubOptions["dubParams"])
        }
        val dubBinary = DubBinaryPathProvider.getDubPath()
        return GeneralCommandLine()
            .withWorkDirectory(workingDirectory)
            .withExePath(dubBinary!!)
            .withParameters(params.parameters)
    }

    /*
    * Must not be run within the EDT
    */
    private fun runDubCommand(project: Project, cmd: GeneralCommandLine) {
        LOG.debug(cmd.commandLineString)
        try {
            val process = OSProcessHandler(cmd.createProcess(), cmd.commandLineString)
            val listener = DubInitListener()
            process.addProcessListener(listener)
            process.startNotify()
            process.waitFor()

            // new way to send a notification (since 2020.3) via notification group defined in plugin.xml
            NotificationGroupManager.getInstance().getNotificationGroup("DLANG_POPUP")
                .createNotification(
                    "Dub init output",
                    listener.output,
                    if (listener.hasErrors()) NotificationType.WARNING else NotificationType.INFORMATION
                )
                .notify(project)
            if (listener.hasErrors()) {
                LOG.warn(listener.output)
            }
        } catch (e: ExecutionException) {
            LOG.warn("There was a problem running 'dub init'", e)
        }
    }

    private class DubInitListener : ProcessAdapter() {
        private val buffer = StringBuffer() // use StringBuffer because StringBuilder is not thread safe
        private val errors = AtomicBoolean()
        val output: String
            get() = buffer.toString()

        fun hasErrors(): Boolean {
            return errors.get()
        }

        override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {
            if (ProcessOutputTypes.STDERR == outputType) {
                errors.set(true)
            }
            buffer.append(LocalTime.now()).append(" [").append(outputType).append("] ").append(event.text)
        }
    }

    companion object {
        private val LOG = Logger.getInstance(
            DlangDubModuleBuilder::class.java
        )
        private const val RUN_DUB_CONFIG_NAME = "Run DUB"
        const val BUILDER_ID = "DLangDubApp"
    }
}
