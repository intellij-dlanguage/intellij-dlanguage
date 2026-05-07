package io.github.intellij.dlanguage.debugger.runconfig

import com.jetbrains.cidr.ArchitectureType
import com.jetbrains.cidr.execution.Installer
import com.jetbrains.cidr.execution.RunParameters
import com.jetbrains.cidr.execution.debugger.backend.DebuggerDriverConfiguration

class DDebugRunParameters(
    val driverConfiguration: DebuggerDriverConfiguration,
    val myInstaller: Installer,
    val architectureType: ArchitectureType
) : RunParameters() {
    override fun getInstaller(): Installer = myInstaller

    override fun getDebuggerDriverConfiguration(): DebuggerDriverConfiguration = driverConfiguration

    override fun getArchitectureId(): String = architectureType.toString()
}
