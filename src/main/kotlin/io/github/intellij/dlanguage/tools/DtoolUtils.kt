package io.github.intellij.dlanguage.tools

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.util.text.StringUtil
import com.intellij.util.text.SemVer
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * @author Samael Bate (singingbush)
 * created on 19/08/18
 */
object DtoolUtils {

    // This list of D Tool versions will need to be manually maintained until we have a web service in place
    const val DMD_LATEST: String = "2.106.0"
    const val LDC_LATEST: String = "1.35.0"

    const val SERVE_D_LATEST: String = "0.7.5"

    const val DUB_LATEST: String = "1.35.0"
    const val DSCANNER_LATEST: String = "v0.15.2" // v0.16.0-beta.4
    const val DCD_LATEST: String = "v0.15.2" // v0.9.10, OR v0.9.10-alpha.0
    const val DFORMAT_LATEST: String = "v0.15.1"


    val STANDARD_TOOL_PATHS: List<Path>


    init {
        if (SystemInfo.isWindows) {
            STANDARD_TOOL_PATHS = listOf(
                Paths.get("\\D\\dmd2\\windows\\bin")
            )
        } else if (SystemInfo.isMac) {
            STANDARD_TOOL_PATHS = listOf(
                Paths.get("/usr/local/opt") // homebrew
            )
        } else if (SystemInfo.isUnix) {
            STANDARD_TOOL_PATHS = listOf(
                Paths.get("/usr/local/bin"),
                Paths.get("/usr/bin"),
                Paths.get("/snap/bin"), // #575 support snaps
                Paths.get(System.getProperty("user.home") + "/bin")
            )
        } else {
            STANDARD_TOOL_PATHS = listOf()
        }
    }

    @JvmStatic
    fun String?.versionPredates(comparison: String?): Boolean {
        return if (!this.isSemVer() && !comparison.isSemVer()) false
        else {
            this.toSemVer()?.let {
                val v = it
                comparison?.toSemVer()?.let {
                    when {
                        it.major != v.major -> it.major > v.major
                        it.minor != v.minor -> it.minor > v.minor
                        else -> it.patch > v.patch
                    }
                }
            } ?: false
        }
    }

    @JvmStatic
    fun String?.toSemVer(): SemVer? {
        // todo: consider deprecating this and just using com.intellij.util.text.SemVer.parseFromText() instead
        return if (this.isSemVer()) {
            val parts = this!!.split(".")
                .filterNot { s -> s.isEmpty() }
                .map {
                    it.split(Regex("\\D"))
                        .filterNot { s -> s.isEmpty() }
                        .first()
                }
                .map {
                    it.toInt()
                }

            return when (parts.size) {
                1 -> SemVer(this, parts[0], 0, 0)
                2 -> SemVer(this, parts[0], parts[1], 0)
                else -> SemVer(this, parts[0], parts[1], parts[2])
            }
        } else null
    }

    @JvmStatic
    fun String?.isSemVer(): Boolean {
        return if (this.isNullOrBlank()) false
        else this.contains(".") &&
            this.split(".")
                .map {
                    it.replace(Regex("\\D"), "")
                }.filterNot {
                    it.isBlank()
                }.isNotEmpty()
    }



    /**
     * Tries to get the absolute path for a command by trying standard installation directories (for dub).
     * This is mostly useful for dub, especially on Mac/Linux where various installation methods can differ
     */
    fun lookInStandardDirectories(binaryName: String): String? {
        for (path in STANDARD_TOOL_PATHS) {
            val toolPath: Path = path.resolve(binaryName)
            if (Files.exists(toolPath) && Files.isExecutable(toolPath)) {
                return toolPath.toAbsolutePath().toString()
            }
        }
        return null
    }


    @JvmStatic
    fun getVersionOutput(cmd: String): String {
        if (StringUtil.isEmptyOrSpaces(cmd) || !Files.isExecutable(Paths.get(cmd)) || Files.isDirectory(Paths.get(cmd))) {
            return ""
        }
        val commandLine = GeneralCommandLine(cmd, "--version")

        val future = ApplicationManager
            .getApplication()
            .executeOnPooledThread<String?>(Callable {
                CapturingProcessHandler(
                    commandLine.createProcess(),
                    commandLine.charset,
                    commandLine.commandLineString
                ).runProcess().getStdout()
            })

        try {
            val versionOutput = future.get(800, TimeUnit.MILLISECONDS)

            if (StringUtil.isNotEmpty(versionOutput)) {
                val version = versionOutput!!.split("\n".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0].trim { it <= ' ' }
                return version
            }
        } catch (_: Exception) {
        }
        return ""
    }
}
