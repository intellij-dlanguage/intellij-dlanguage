package io.github.intellij.dub.run

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ColoredProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.VirtualFile
import io.github.intellij.dlanguage.utils.DToolsNotificationAction
import io.github.intellij.dlanguage.utils.DUtil
import io.github.intellij.dub.service.DubBinaryPathProvider
import java.nio.file.Paths
import java.util.*

class DlangRunDubState internal constructor(
    environment: ExecutionEnvironment,
    private val config: DlangRunDubConfiguration
) : CommandLineState(environment) {
    @Throws(ExecutionException::class)
    override fun startProcess(): ProcessHandler {
        return try {
            val dubCommandLine = getExecutableCommandLine(config)
            val handler: ProcessHandler =
                ColoredProcessHandler(dubCommandLine.createProcess(), dubCommandLine.commandLineString)
            ProcessTerminatedListener.attach(handler, config.project)
            handler
        } catch (e: ExecutionException) {
            val message = e.message
            val project = config.project
            val isEmpty = message == "DUB executable is not specified"
            val notCorrect = message!!.startsWith("Cannot run program")
            if (isEmpty || notCorrect) {
                NotificationGroupManager.getInstance()
                    .getNotificationGroup("DUB run configuration")
                    .createNotification(
                        "DUB settings",
                        "DUB executable is ${if (isEmpty) "not specified" else "not specified correctly"}",
                        NotificationType.ERROR
                    )
                    .addAction(DToolsNotificationAction("Configure"))
                    .notify(project)
            }
            throw e
        }
    }

    /* Build command line to start DUB executable
     */
    @Throws(ExecutionException::class)
    private fun getExecutableCommandLine(config: DlangRunDubConfiguration): GeneralCommandLine {
        val module = config.configurationModule!!.module
            ?: throw ExecutionException("Run configuration has no module selected.")
        val dubPath = DubBinaryPathProvider.getDubPath()
        if (StringUtil.isEmptyOrSpaces(dubPath)) {
            throw ExecutionException("DUB executable is not specified")
        }
        if (!Paths.get(dubPath!!).toFile().canExecute()) {
            throw ExecutionException("DUB is not configured correctly")
        }
        val sourcesRoot = getSourceRoot(module)
        val cmd = GeneralCommandLine()
        cmd.exePath = dubPath
        if (StringUtil.isNotEmpty(config.workingDir)) {
            cmd.withWorkDirectory(config.workingDir)
        } else {
            cmd.withWorkDirectory(config.project.basePath)
        }

        // Add command line parameters
        val toBuild = config.generalDubOptions == 0
        val toRun = config.generalDubOptions == 1
        val toTest = config.generalDubOptions == 2
        if (toBuild) {
            cmd.addParameter("build")
        } else if (toTest) {
            cmd.addParameter("test")
        } else if (toRun) {
            cmd.addParameter("run")
        }
        if (toRun) {
            if (config.isCbTempBuild) {
                cmd.addParameter("--temp-build")
            }
        }
        if (toTest) {
            if (config.isCbCoverage) {
                cmd.addParameter("--coverage")
            }
            if (StringUtil.isNotEmpty(config.tfMainFile)) {
                cmd.addParameter("--main-file")
                cmd.addParameter(config.tfMainFile!!)
            }
        }
        if (config.isCbRdmd) {
            cmd.addParameter("--rdmd")
        }
        if (config.isCbForce) {
            cmd.addParameter("--force")
        }
        if (config.isCbNoDeps) {
            cmd.addParameter("--nodeps")
        }
        if (config.isCbForceRemove) {
            cmd.addParameter("--force-remove")
        }
        if (config.isCbCombined) {
            cmd.addParameter("--combined")
        }
        if (config.isCbParallel) {
            cmd.addParameter("--parallel")
        }
        if (config.isQuiet) {
            cmd.addParameter("-q")
        }
        if (config.isVerbose) {
            cmd.addParameter("-v")
        }
        if (DUtil.isNotNullOrEmpty(config.tfArch)) {
            cmd.addParameter("--arch")
            cmd.addParameter(config.tfArch!!)
        }
        if (DUtil.isNotNullOrEmpty(config.tfBuild)) {
            cmd.addParameter("--build")
            cmd.addParameter(config.tfBuild!!)
        }
        if (DUtil.isNotNullOrEmpty(config.tfConfig)) {
            cmd.addParameter("--config")
            cmd.addParameter(config.tfConfig!!)
        }
        if (DUtil.isNotNullOrEmpty(config.tfDebug)) {
            cmd.addParameter("--debug")
            cmd.addParameter(config.tfDebug!!)
        }
        if (DUtil.isNotNullOrEmpty(config.tfCompiler)) {
            cmd.addParameter("--compiler")
            cmd.addParameter(config.tfCompiler!!)
        }
        val bmSeparate = config.buildMode == 0
        val bmAll = config.buildMode == 1
        val bmSingle = config.buildMode == 2
        if (bmSeparate) {
            cmd.addParameter("--build-mode")
            cmd.addParameter("separate")
        }
        if (bmAll) {
            cmd.addParameter("--build-mode")
            cmd.addParameter("allAtOnce")
        }
        if (bmSingle) {
            cmd.addParameter("--build-mode")
            cmd.addParameter("singleFile")
        }
        if (DUtil.isNotNullOrEmpty(config.additionalParams)) {
            cmd.addParameters(
                Arrays.asList(
                    *config.additionalParams!!.split("\\s".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()))
        }
        cmd.withEnvironment(config.envVars)
        return cmd
    }

    private fun getSourceRoot(module: Module?): VirtualFile? {
        if (module != null && !module.isDisposed) {
            val sourcesRoots = ModuleRootManager.getInstance(module).sourceRoots
            if (sourcesRoots.isNotEmpty()) {
                return sourcesRoots[0]
            }
        }
        return null
    }
}
