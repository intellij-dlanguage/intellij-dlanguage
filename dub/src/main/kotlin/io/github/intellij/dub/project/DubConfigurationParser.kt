package io.github.intellij.dub.project

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.execution.process.OSProcessHandler
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import io.github.intellij.dub.project.DubConfigurationParser
import io.github.intellij.dub.tools.DescribeParser
import io.github.intellij.dub.tools.DescribeParserException
import io.github.intellij.dub.tools.DescribeParserImpl
import io.github.intellij.dub.tools.DubProcessListener
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.function.Consumer
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreeNode

/**
 * This class is used to run 'dub describe' which outputs project info in json format. We parse the json
 * to gather up information about the projects dependencies
 */
class DubConfigurationParser @JvmOverloads constructor(
    private val project: Project,
    private val dubBinaryPath: String,
    private val silentMode: Boolean = false,
    private val moduleDir: String = ""
) {
    private val parser: DescribeParser
    /**
     * @param project A valid D project
     * @param dubBinaryPath The location of the dub binary
     * @param silentMode When set to true notifications will not show in the UI
     * @since v1.16
     */
    /**
     * DO NOT REMOVE - This is required for backward compatibility
     * @param project A valid D project
     * @param dubBinaryPath The location of the dub binary
     */
    init {
        parser = DescribeParserImpl()
    }

    val dubProjectImportPaths: List<String?>
        /**
         * Runs <pre>dub describe --import-paths --vquiet</pre> to get import paths.
         * @return Source paths for the current project and any included dub dependencies
         * @since v1.28.1
         */
        get() {
            if (canUseDub()) {
                val optionalFuture = ApplicationManager.getApplication()
                    .executeOnPooledThread<List<String?>> {
                        importPaths(
                            project.basePath, dubBinaryPath
                        )
                    }
                try {
                    return optionalFuture.get()
                } catch (e: InterruptedException) {
                    LOG.error("There was a problem running 'dub describe --import-paths --vquiet'", e)
                } catch (e: ExecutionException) {
                    LOG.error("There was a problem running 'dub describe --import-paths --vquiet'", e)
                }
            }
            throw RuntimeException(String.format("dub binary '%s' is not usable", dubBinaryPath))
        }
    val dubProject: Optional<DubProject>
        /**
         *
         * @return a [io.github.intellij.dub.project.DubProject] is a data class containing lots of useful details returned from 'dub describe'
         *
         * @since v1.16.2
         */
        get() {
            if (canUseDub()) {
                val optionalFuture = ApplicationManager.getApplication()
                    .executeOnPooledThread<Optional<DubProject>> { parseDubConfiguration(silentMode) }
                try {
                    return optionalFuture[10L, TimeUnit.SECONDS] // Yes 'dub describe' is slow, especially on 1st run
                } catch (e: InterruptedException) {
                    LOG.error("Call to dub timed out", e)
                } catch (e: ExecutionException) {
                    LOG.error("Call to dub timed out", e)
                } catch (e: TimeoutException) {
                    LOG.error("Call to dub timed out", e)
                }
            }
            return Optional.empty()
        }

    @get:Deprecated("")
    val dubPackage: Optional<DubPackage>
        /**
         * This method is now Deprecated as it makes more sense to users of this class to use getDubProject()
         * @return the root DubPackage for this project
         */
        get() {
            LOG.warn("Call to deprecated method getDubPackage()")
            val dubProject = dubProject
            return dubProject.map(DubProject::rootPackage)
        }

    /**
     * @return true if a path to a dub binary is configured and the project has a dub.sdl or dub.json
     */
    fun canUseDub(): Boolean {
        // For wsl, dub command can have any file name not only dub/dub.exe
        val dubPathValid = StringUtil.isNotEmpty(dubBinaryPath)
        val baseDir = if (moduleDir.isEmpty()) project.guessProjectDir() else LocalFileSystem.getInstance().findFileByPath(moduleDir)
        if (baseDir == null || !baseDir.exists())
            return false

        baseDir.refresh(true, true)
        return dubPathValid && Arrays.stream(baseDir.children)
            .filter { f: VirtualFile -> !f.isDirectory }
            .anyMatch { file: VirtualFile ->
                "dub.json".equals(
                    file.name,
                    ignoreCase = true
                ) || "dub.sdl".equals(file.name, ignoreCase = true)
            }
    }

    @get:Deprecated("")
    val dubPackageDependencies: List<DubPackage>
        /**
         * This method is now Deprecated as it makes more sense to users of this class to use getDubProject()
         * @return a list of DubPackage that the root DubPackage depends on. These may be sub-packages, libs, or other dub packages
         */
        get() {
            val dubProject = dubProject
            return dubProject.map(DubProject::packages).orElse(emptyList())
        }
    val packageTree: TreeNode?
        /**
         * lazilly evaluated. This isn't actually used yet so is open for changes. Plan is to use it from DUB plugin
         * @return A Tree for use in Swing UI components
         */
        get() {
            val dubProject = dubProject
            return dubProject.map { (_, rootPackage): DubProject -> buildDependencyTree(rootPackage) }
                .orElse(null)
        }

    private fun buildDependencyTree(dubPackage: DubPackage): DefaultMutableTreeNode {
        val treeNode = DefaultMutableTreeNode(dubPackage, !dubPackage.dependencies.isEmpty())
        dubPackageDependencies.stream()
            .filter { (name): DubPackage -> dubPackage.dependencies.contains(name) }
            .forEach { dependency: DubPackage -> treeNode.add(buildDependencyTree(dependency)) }
        return treeNode
    }

    /*
     * dub describe --import-paths --vquiet
     */
    @Throws(com.intellij.execution.ExecutionException::class)
    private fun importPaths(workingDirectory: String?, dubBinaryPath: String): List<String?> {
        val cmd = GeneralCommandLine()
            .withWorkDirectory(workingDirectory)
            .withExePath(dubBinaryPath)
            .withParameters("describe", "--import-paths", "--vquiet")
        val processHandler = CapturingProcessHandler(cmd)
        return processHandler.runProcess().getStdoutLines(true)
    }

    private fun parseDubConfiguration(silentMode: Boolean): Optional<DubProject> {
        val projectDir = if (moduleDir.isEmpty()) project.guessProjectDir() else LocalFileSystem.getInstance().findFileByPath(moduleDir)
        if (projectDir == null || !projectDir.exists() || StringUtil.isEmptyOrSpaces(dubBinaryPath)) {
            return Optional.empty()
        }
        if (!Files.isExecutable(
                Paths.get(
                    StringUtil.trim(
                        dubBinaryPath
                    )
                )
            )
        ) {
            LOG.warn("Cannot run dub as path is not executable")
            return Optional.empty()
        }
        try {
            val cmd = GeneralCommandLine()
                .withWorkDirectory(projectDir.canonicalPath)
                .withExePath(StringUtil.trim(dubBinaryPath))
                .withParameters("describe")
            val dubCommand = cmd.commandLineString
            val listener = DubProcessListener()
            val process = OSProcessHandler(cmd.createProcess(), dubCommand)
            process.addProcessListener(listener)
            process.startNotify()
            process.waitFor()
            val exitCode = process.exitCode
            val errors = listener.getErrors()
            if (exitCode != null && exitCode == 0) {
                if (errors.isEmpty()) {
                    LOG.info(String.format("%s exited without errors", dubCommand))
                    if (LOG.isDebugEnabled) {
                        NotificationGroupManager.getInstance()
                            .getNotificationGroup("DubNotification")
                            .createNotification(
                                "DUB Import",
                                "dub project imported without errors",
                                NotificationType.INFORMATION
                            )
                            .notify(project)
                    }
                } else {
                    if (!silentMode) {
                        LOG.warn(String.format("%s exited with %s errors", dubCommand, errors.size))
                    } else {
                        LOG.debug(String.format("%s exited with %s errors", dubCommand, errors.size))
                    }
                    // potential error messages are things like:
                    //   "No valid root package found - aborting."
                    //   "Package vibe-d declared a sub-package, definition file is missing: /path/to/package"
                    //   "Non-optional dependency vibe-d:core of vibe-d not found in dependency tree!?."
                    if (!silentMode) {
                        errors.forEach(
                            Consumer { errorMessage: String? ->
                                NotificationGroupManager.getInstance()
                                    .getNotificationGroup("DubNotification")
                                    .createNotification(
                                        "DUB Import Error",
                                        errorMessage!!,
                                        NotificationType.WARNING
                                    )
                                    .notify(project)
                            }
                        )
                    }
                }
                return Optional.of(parser.parse(listener.getStdOut()))
            } else {
                errors.forEach(Consumer { message: String? -> LOG.warn(message) })
                val message = String.format("%s exited with %s:\n%s", dubCommand, exitCode, errors[0])
                LOG.warn(message)
                if (!silentMode) {
                    ApplicationManager.getApplication().invokeLater({ Messages.showErrorDialog(project, message, "Dub Import") })
                }
            }
        } catch (e: com.intellij.execution.ExecutionException) {
            LOG.error("Unable to parse dub configuration", e)
            e.printStackTrace()
        } catch (e: DescribeParserException) {
            LOG.error("Unable to parse dub configuration", e)
            e.printStackTrace()
        }
        return Optional.empty()
    }

    companion object {
        private val LOG = Logger.getInstance(
            DubConfigurationParser::class.java
        )
    }
}
