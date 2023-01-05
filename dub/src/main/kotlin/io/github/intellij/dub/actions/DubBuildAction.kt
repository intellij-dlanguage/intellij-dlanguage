package io.github.intellij.dub.actions

import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.impl.RunManagerImpl
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.module.ModuleManager
import io.github.intellij.dlanguage.run.DlangRunDubConfiguration
import io.github.intellij.dlanguage.run.DlangRunDubConfigurationType
import io.github.intellij.dlanguage.run.DubBuildRunner

/**
 * @author Samael Bate (singingbush)
 * created on 31/03/2017
 */
class DubBuildAction : DubAction("_Run Dub", "", AllIcons.Actions.Execute) {

    private val LOG: Logger = Logger.getInstance(DubBuildAction::class.java)

    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let {
            val runManager = RunManagerImpl.getInstanceImpl(it)
            val projectName = it.name
            val configName = "$projectName [dub run]"

            var runDubSettings = runManager.findConfigurationByName(configName)

            if (runDubSettings == null) {
                val runDubConfigurationType = ConfigurationType.CONFIGURATION_TYPE_EP.findExtensionOrFail(DlangRunDubConfigurationType::class.java)
                val factory = runDubConfigurationType.configurationFactories[0]
                runDubSettings = runManager.createConfiguration(configName, factory)

                val config = runDubSettings.configuration as DlangRunDubConfiguration

                config.generalDubOptions = 1 // 0: build, 1: run, 2: test

                // todo: find a way for the ToggleOffline action to set the following:
                //config.isCbNoDeps = true // '--nodeps' -> "Do not check/update dependencies before building"
                //config.additionalParams = "--skip-registry=all" // currently no config option for '--skip-registry={none|standard|all}'

                ModuleManager.getInstance(it).modules
                        .find { module -> module.name == projectName }
                        ?.let { config.setModule(it) }

                runManager.addConfiguration(runDubSettings, false)
            }

            runManager.selectedConfiguration = runDubSettings

            // now actually start the process
            val dubBuildRunner = DubBuildRunner.PROGRAM_RUNNER_EP.findExtensionOrFail(DubBuildRunner::class.java)
            val env = ExecutionEnvironment(DefaultRunExecutor(), dubBuildRunner, runDubSettings, it)
            dubBuildRunner.execute(env) { LOG.info("DubBuildRunner started") }
        }

    }
}
