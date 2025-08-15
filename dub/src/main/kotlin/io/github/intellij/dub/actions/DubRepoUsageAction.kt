package io.github.intellij.dub.actions

import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.progress.impl.BackgroundableProcessIndicator
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.util.SystemProperties
import io.github.intellij.dub.DubBundle
import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.io.path.exists

/**
 * A users local dun repo (~/dub) can get large over time. If left for a number of years it can waste gigabytes
 * of disk space. This action should be triggered (either manually or automatically) to scan the users local dub
 * repository directory and suggest directories that can be removed (older dependencies that are no longer being accessed).
 *
 * This should be based on the build cache
 *
 * @author Samael Bate (singingbush)
 * created on 10/08/2025
 */
class DubRepoUsageAction : AnAction(
    DubBundle.message("dub.actions.repo-usage.text"),
    DubBundle.message("dub.actions.repo-usage.description"),
    null
), DumbAware {

    private companion object {
        const val ONE_GiB: Long = FileUtils.ONE_GB // 1_073_741_824L (2^30 bytes)
        const val NOTIFICATION_GROUP_ID = "DubNotification"
    }

    private val log = Logger.getInstance(DubRepoUsageAction::class.java)

    val localDubRepoCachePath: Path = getUsersDubRepoDirectory("cache")
    val localDubRepoPackagesPath: Path = getUsersDubRepoDirectory("packages")

    /*
     * On unix is should be '~/.dub' and on Windows it's '%LOCALAPPDATA%/dub'
     */
    private fun getUsersDubRepoDirectory(subDir: String): Path {
        return if (SystemInfo.isUnix)
            Paths.get(SystemProperties.getUserHome(), ".dub", subDir) else
            Paths.get(System.getenv("LOCALAPPDATA"), "dub", subDir)
    }

    /**
     * Only enable the action in the UI of the IDE if the ~/.dub/packages and ~/.dub/cache exist
     */
    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = localDubRepoCachePath.exists() && localDubRepoPackagesPath.exists()
    }

    override fun actionPerformed(e: AnActionEvent) {
        if (!localDubRepoCachePath.exists() || !localDubRepoPackagesPath.exists()) {
            // should not happen as the update function should ensure the UI is only enabled if the path exists
            return
        }
        val project = e.project

        project?.let { project ->
            if (project.isDisposed) {
                return
            }
            val usersDubCacheDir = localDubRepoCachePath.toFile()
            if (!usersDubCacheDir.canRead()) {
                log.warn("Cannot read path: $localDubRepoCachePath")
                return
            }

            val usersDubPackagesDir = localDubRepoPackagesPath.toFile()
            if (!usersDubPackagesDir.canRead()) {
                log.warn("Cannot read path: $localDubRepoPackagesPath")
                return
            }

            run {
                val scanDubRepoTask = ScanLocalDubRepoTask(project, usersDubCacheDir, usersDubPackagesDir)

                ProgressManager.getInstance()
                    .runProcessWithProgressAsynchronously(
                        scanDubRepoTask,
                        BackgroundableProcessIndicator(scanDubRepoTask)
                    )
            }
        }
    }

    override fun getActionUpdateThread() = ActionUpdateThread.EDT

    class ScanLocalDubRepoTask(val p: Project, val usersDubCacheDir: File, val usersDubPackagesDir: File) : Task.Backgroundable(p, "Scanning local dub repository") {
        private val log = Logger.getInstance(ScanLocalDubRepoTask::class.java)

        override fun run(indicator: ProgressIndicator) {
            // todo: let the user configure the threshold (default to 1GB) somewhere in settings
            val cacheDirSize = usersDubCacheDir.walkTopDown().filter { it.isFile }.map { it.length() }.sum()

            val packagesDirSize = usersDubPackagesDir.walkTopDown().filter { it.isFile }.map { it.length() }.sum()

            val totalSizeBytes = cacheDirSize + packagesDirSize

            if (totalSizeBytes > ONE_GiB)
                log.info("local dub repo (packages & cache) is using ${StringUtil.formatFileSize(cacheDirSize)} of space")

            // todo: allow the user to define a number of months or years that is considered old enough to no longer need
            val packageAgeThreshold = ZonedDateTime.now(ZoneId.of("UTC")).minusYears(1).toInstant()

            // go through all dub packages looking for old packages
            val dubCacheDirectories = usersDubCacheDir.listFiles()
            val dubPackageDirectories = usersDubPackagesDir.listFiles()
            val progressIncrement = 1.0 / (dubCacheDirectories.size + dubPackageDirectories.size)

            // status indicator needs to allow progress update
            indicator.isIndeterminate = false

            val oldCaches = HashMap<String,File>()
            dubCacheDirectories.forEach { packageDir ->
                if (packageDir.isDirectory) {
                    packageDir.listFiles().forEach { versionDir ->
                        if (versionDir.isDirectory) {
                            indicator.text = "${packageDir.name} ${versionDir.name}"

                            val lastUpdated = Instant.ofEpochMilli(versionDir.lastModified())

                            if (lastUpdated.isBefore(packageAgeThreshold)) {
                                oldCaches["${packageDir.name} ${versionDir.name}"] = versionDir
                            }
                        }
                    }
                }
                indicator.fraction += progressIncrement
            }

            val oldPackages = HashMap<String,File>()
            dubPackageDirectories.forEach { packageDir ->
                if (packageDir.isDirectory) {
                    packageDir.listFiles().forEach { versionDir ->
                        if (versionDir.isDirectory) {
                            indicator.text = "${packageDir.name} ${versionDir.name}"

                            val lastUpdated = Instant.ofEpochMilli(versionDir.lastModified())

                            if (lastUpdated.isBefore(packageAgeThreshold)) {
                                oldPackages["${packageDir.name} ${versionDir.name}"] = versionDir
                            }
                        }
                    }
                }
                indicator.fraction += progressIncrement
            }

            // note that neither Jetbrains's StringUtil.formatFileSize or Apache's FileUtils.byteCountToDisplaySize
            // format large byte sizes properly. See:
            // https://youtrack.jetbrains.com/issue/IJSDK-2627
            // https://issues.apache.org/jira/browse/IO-876
            // For now we're using Jetbrains code but it may be worth writing our own
            val totalSizeTxt = StringUtil.formatFileSize(totalSizeBytes)

            val allOldDirectories: List<File> = oldCaches.values + oldPackages.values

            if (totalSizeBytes > ONE_GiB && (oldCaches.isNotEmpty() || oldPackages.isNotEmpty())) {

                NotificationGroupManager.getInstance()
                    .getNotificationGroup(NOTIFICATION_GROUP_ID)
                    .createNotification(
                        DubBundle.message("dub.actions.repo-usage.notification.title", "1 GiB"),
                        DubBundle.message("dub.actions.repo-usage.notification.content", totalSizeTxt, oldPackages.size, oldCaches.size),
                        NotificationType.INFORMATION
                    )
                    .setImportantSuggestion(true)
                    .addAction(NotificationAction.createExpiring("Remove ${allOldDirectories.size} directories", {
                        _,_ -> allOldDirectories.forEach {
                            log.debug("deleting: ${it.absolutePath}")
                            FileUtil.deleteRecursively(it.toPath())
                        }
                        NotificationGroupManager.getInstance()
                            .getNotificationGroup(NOTIFICATION_GROUP_ID)
                            .createNotification(
                                DubBundle.message("dub.actions.repo-usage.update.notification.title"),
                                DubBundle.message("dub.actions.repo-usage.removed.notification.content", allOldDirectories.size),
                                NotificationType.INFORMATION
                            )
                            .notify(project)
                    }))
                    .notify(project)
            } else {
                NotificationGroupManager.getInstance()
                    .getNotificationGroup(NOTIFICATION_GROUP_ID)
                    .createNotification(
                        DubBundle.message("dub.actions.repo-usage.update.notification.title"),
                        DubBundle.message("dub.actions.repo-usage.update.notification.content", totalSizeTxt),
                        NotificationType.INFORMATION
                    )
                    .notify(project)
            }
        }
    }
}
