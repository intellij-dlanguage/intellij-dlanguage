package io.github.intellij.dub.run

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.execution.executors.DefaultDebugExecutor
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.runners.GenericProgramRunner
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.InvalidDataException
import com.intellij.openapi.util.WriteExternalException
import io.github.intellij.dlanguage.run.DlangRunAppConfiguration
import io.github.intellij.dlanguage.run.DlangRunAppState
import io.github.intellij.dlanguage.run.RunUtil
import io.github.intellij.dlanguage.run.exception.ModuleNotFoundException
import io.github.intellij.dlanguage.run.exception.NoValidDlangSdkFound
import io.github.intellij.dub.run.RunAppRunner.DubAppSettings
import org.jdom.Element

/**
 * Since DefaultProgramRunner is deprecated this class was updated to use GenericProgramRunner.
 * It may be worth investigating the use of AsyncProgramRunner or perhaps simply the implementing
 * the ProgramRunner<Settings extends RunnerSettings> interface
</Settings> */
class RunAppRunner : GenericProgramRunner<DubAppSettings>() {
    override fun getRunnerId(): String {
        return javaClass.simpleName
    }

    override fun canRun(executorId: String, profile: RunProfile): Boolean {
        return (DefaultDebugExecutor.EXECUTOR_ID == executorId || DefaultRunExecutor.EXECUTOR_ID == executorId) &&
                profile is DlangRunAppConfiguration
    }

    @Throws(ExecutionException::class)
    override fun doExecute(state: RunProfileState, environment: ExecutionEnvironment): RunContentDescriptor? {
        if (DefaultDebugExecutor.EXECUTOR_ID == environment.executor.actionName) {
            try {
                val dlangRunAppState = state as DlangRunAppState
                val executableFilePath = dlangRunAppState.getExecutableCommandLine(dlangRunAppState.config)
                    .exePath
                    .replace("\\", "/")
                return RunUtil.startDebugger(this, state, environment, environment.project, environment.executor, executableFilePath)
            } catch (e: ModuleNotFoundException) {
                e.printStackTrace()
                log.error(e.toString())
            } catch (e: NoValidDlangSdkFound) {
                log.warn("Unable to run with DMD", e)
            }
        }
        return super.doExecute(state, environment)
    }

    class DubAppSettings : RunnerSettings {
        @Throws(InvalidDataException::class)
        override fun readExternal(element: Element) {
        }

        @Throws(WriteExternalException::class)
        override fun writeExternal(element: Element) {
        }
    }

    companion object {
        private val log = Logger.getInstance(
            RunAppRunner::class.java
        )
    }
}
