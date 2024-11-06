package io.github.intellij.dlanguage.debugger.runconfig

import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.filters.Filter
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.util.system.CpuArch
import com.intellij.xdebugger.XDebugProcess
import com.intellij.xdebugger.XDebugProcessStarter
import com.intellij.xdebugger.XDebugSession
import com.intellij.xdebugger.XDebuggerManager
import com.jetbrains.cidr.ArchitectureType
import com.jetbrains.cidr.execution.TrivialInstaller
import com.jetbrains.cidr.execution.debugger.CidrLocalDebugProcess
import com.jetbrains.cidr.execution.debugger.backend.gdb.GDBDriverConfiguration
import com.jetbrains.cidr.execution.debugger.backend.lldb.LLDBDriverConfiguration
import io.github.intellij.dlanguage.settings.ToolKey

class DGDBDriverConfiguration() : GDBDriverConfiguration() {
    override fun isElevated(): Boolean = false
    override fun emulateTerminal(): Boolean = false
    override fun isAttachSupported(): Boolean = false
    override fun getDriverName(): String = "Dlang GDB"
    override fun getGDBExecutablePath(): String {
        // TODO gdb path
        return ToolKey.GDB_KEY.path?: super.gdbExecutablePath
    }
}

class DLLDBDriverConfiguration() : LLDBDriverConfiguration() {
    override fun isElevated(): Boolean = false
    override fun emulateTerminal(): Boolean = false
    override fun isAttachSupported(): Boolean = false
    override fun getDriverName(): String = "Dlang LLDB"
}

fun showRunContent(state: CommandLineState, environment: ExecutionEnvironment, cmd: GeneralCommandLine): RunContentDescriptor {
    // TODO GDB or LLDB
    val runParameters = DDebugRunParameters(DGDBDriverConfiguration(), TrivialInstaller(cmd), ArchitectureType.forVmCpuArch(CpuArch.CURRENT))
    return XDebuggerManager.getInstance(environment.project)
        .startSession(environment, object : XDebugProcessStarter() {
            override fun start(session: XDebugSession): XDebugProcess =
                CidrLocalDebugProcess(runParameters, session, state.consoleBuilder, { Filter.EMPTY_ARRAY }, false).apply {
                    ProcessTerminatedListener.attach(processHandler, environment.project)
                    start()
                }
        }).runContentDescriptor
}
