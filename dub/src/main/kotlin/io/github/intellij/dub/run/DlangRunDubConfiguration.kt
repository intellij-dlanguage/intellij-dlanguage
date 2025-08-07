package io.github.intellij.dub.run

import com.intellij.execution.ExecutionException
import com.intellij.execution.Executor
import com.intellij.execution.configurations.*
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.InvalidDataException
import com.intellij.openapi.util.WriteExternalException
import com.intellij.util.xmlb.XmlSerializer
import io.github.intellij.dlanguage.run.DMDRunner
import org.jdom.Element

class DlangRunDubConfiguration(name: String?, project: Project?, factory: ConfigurationFactory?) :
    ModuleBasedConfiguration<RunConfigurationModule?, Module?>(
        name, RunConfigurationModule(
            project!!
        ), factory!!
    ) {
    //General tab
    var generalDubOptions = 1

    //Build tab
    var isCbRdmd = false
    var tfBuild: String? = null
    var tfCompiler: String? = null

    // Run tab
    var tfConfig: String? = null
    var tfArch: String? = null
    var tfDebug: String? = null
    var buildMode = 0
    var isCbForce = false
    var isCbNoDeps = false
    var isCbForceRemove = false
    var isCbCombined = false
    var isCbParallel = false
    var isCbTempBuild = false

    // Test tab
    var tfMainFile: String? = null
    var isCbCoverage = false

    //DUB properties
    var workingDir: String? = null
    var isQuiet = false
    var isVerbose = false
    var additionalParams: String? = null
    var envVars: Map<String, String>?

    init {
        val modules = this.validModules
        if (!modules.isEmpty()) {
            //Get first valid module and use it
            setModule(modules.iterator().next())
        }
        envVars = HashMap()
    }

    override fun getValidModules(): Collection<Module> {
        val modules = ModuleManager.getInstance(project).modules
        val appRunner = DMDRunner()
        val res = ArrayList<Module>()
        for (module in modules) {
            if (appRunner.isValidModule(module)) {
                res.add(module)
            }
        }
        return res
    }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration?> {
        return DlangRunDubConfigurationEditor(project)
    }

    @Throws(ExecutionException::class)
    override fun getState(executor: Executor, env: ExecutionEnvironment): RunProfileState {
        return DlangRunDubState(env, this)
    }

    @Throws(WriteExternalException::class)
    override fun writeExternal(element: Element) {
        if (envVars == null) {
            envVars = HashMap()
        }
        super.writeExternal(element)
        XmlSerializer.serializeInto(this, element)
    }

    @Throws(InvalidDataException::class)
    override fun readExternal(element: Element) {
        super.readExternal(element)
        readModule(element)
        XmlSerializer.deserializeInto(this, element)
    }
}
