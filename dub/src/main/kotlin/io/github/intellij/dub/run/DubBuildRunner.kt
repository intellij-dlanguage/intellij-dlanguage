package io.github.intellij.dub.run

import com.intellij.execution.ExecutionException
import com.intellij.execution.ExecutionManager
import com.intellij.execution.Executor
import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.execution.executors.DefaultDebugExecutor
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.runners.GenericProgramRunner
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.InvalidDataException
import com.intellij.openapi.util.WriteExternalException
import io.github.intellij.dlanguage.run.RunUtil
import io.github.intellij.dlanguage.settings.ToolKey
import io.github.intellij.dub.project.DubConfigurationParser
import io.github.intellij.dub.project.DubPackage
import org.jdom.Element
import java.nio.file.Paths

/**
 * Since [com.intellij.execution.runners.DefaultProgramRunner] is now deprecated this class was updated to use GenericProgramRunner.
 * It may be worth investigating the use of AsyncProgramRunner or perhaps simply the implementing
 * the ProgramRunner<Settings extends RunnerSettings> interface
</Settings> */
class DubBuildRunner : GenericProgramRunner<DubBuildRunner.DubBuildSettings>() {
    override fun getRunnerId(): String = javaClass.simpleName

    override fun canRun(executorId: String, profile: RunProfile): Boolean {
        return (DefaultDebugExecutor.EXECUTOR_ID == executorId || DefaultRunExecutor.EXECUTOR_ID == executorId) && profile is DlangRunDubConfiguration
    }

    @Throws(ExecutionException::class)
    override fun doExecute(state: RunProfileState, environment: ExecutionEnvironment): RunContentDescriptor? {
        val executor: Executor = environment.executor
        if (DefaultDebugExecutor.EXECUTOR_ID == executor.actionName) {
            val project: Project = environment.project
            var executableFilePath = project.basePath + "/" + project.name
            val dubParser = DubConfigurationParser(project, ToolKey.DUB_KEY.path!!, false)
            if (dubParser.canUseDub() && dubParser.dubProject.isPresent) {
                val rootPackage: DubPackage = dubParser.dubProject.get().rootPackage
                executableFilePath = Paths.get(
                    rootPackage.path,
                    rootPackage.targetPath,
                    rootPackage.targetFileName
                ).toString().replace("\\", "/")
                log.debug("Using root package of dub project for executable path: ", executableFilePath)
            }
            return RunUtil.startDebugger(this, state, environment, project, executor, executableFilePath)
        }
        val result = state.execute(environment.executor, this) ?: return null
        return RunContentDescriptor(result.executionConsole, result.processHandler, result.executionConsole.component, environment.runProfile.name)
    }

    class DubBuildSettings : RunnerSettings {
        @Throws(InvalidDataException::class)
        override fun readExternal(element: Element) {
        }

        @Throws(WriteExternalException::class)
        override fun writeExternal(element: Element) {
        }
    }

    companion object {
        private val log = Logger.getInstance(
            DubBuildRunner::class.java
        )
    }
}
