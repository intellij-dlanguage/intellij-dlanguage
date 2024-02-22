package io.github.intellij.dlanguage.sdk

import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.SdkModificator
import com.intellij.openapi.roots.OrderRootType
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.vfs.LocalFileSystem
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangBundle
import java.io.File
import java.nio.file.Paths
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.swing.Icon

class DlangLdcSdkType : DlangDependentSdkType(SDK_TYPE_ID, SDK_NAME, LDC_BINARY_NAME) {
    companion object {
        const val SDK_TYPE_ID = "LDC"
        const val SDK_NAME = "LDC SDK"
        const val LDC_BINARY_NAME = "ldc2"
    }

//    val defaultBinaryPaths = if(SystemInfo.isWindows) {
//        // todo: work out LDC paths for Windows
//        // C:\Program Files\LDC x.xx\bin
//        // C:\LDC\ldc2-x.xx.x-windows-multilib\bin
//        emptyArray<String>()
//    } else if(SystemInfo.isMac) {
//        arrayOf(
//            "/usr/local/bin", // dmg install from downloads.dlang.org
//            "/opt/homebrew", // installed via Homebrew (when using newer ARM based Mac)
//            "/usr/local/opt", // installed via Homebrew (prior to ARM)
//            "/opt/local/bin" // installed via MacPorts
//        )
//    } else {
//        // the order of these locations is based on the order of how they typically appear in a users $PATH
//        arrayOf(
//            "/usr/local/bin",
//            "/usr/bin", // Fedora and Arch Linux use this path
//            "/snap/bin" // snapcraft.io
//        )
//    }
    val defaultPhobosAndDruntimePaths = arrayOf(
        // todo: get more clever with this path, perhaps just parse ldc2.conf (also paths on other distros)
        "/usr/lib/ldc/x86_64-redhat-linux-gnu/include/d", // Fedora (ldc package in distro repo)
    )

    val versionRegexPattern = Pattern.compile("LDC.*\\((?<version>[\\d.]+)\\).*")

    override fun getCompilerConfigFilename(): String = "ldc2.conf"

    // on Windows expect Druntime to be in "C:\Program Files\LDC x.xx\import" or "C:\tools\ldc2-1.32.2-windows-multilib\import\"
    override fun attachDruntimeSources(sdkModificator: SdkModificator, status: SetupStatus) {
        if (SystemInfo.isWindows) {
            val importDir = File("C:\\Program Files")
                .walk(FileWalkDirection.TOP_DOWN)
                .maxDepth(3)
                .firstOrNull { it.name == "import" }

            importDir?.let {
                val virtualFile = LocalFileSystem.getInstance().findFileByIoFile(it)
                if (virtualFile != null) {
                    sdkModificator.addRoot(virtualFile, OrderRootType.SOURCES)
                    status.runtime = true
                    status.phobos = true
                } else {
                    status.runtime = false
                    status.phobos = false
                }
            }
        } else if (SystemInfo.isLinux) {
            val compilerSources = firstVirtualFileFrom(defaultPhobosAndDruntimePaths)
            if (compilerSources.isPresent) {
                LOG.info("Attaching both Druntime & Phobos sources for LDC")
                sdkModificator.addRoot(compilerSources.get(), OrderRootType.SOURCES)
                status.runtime = true
                status.phobos = true // both druntime & phobos are in same path
            } else {
                status.runtime = false
                status.phobos = false
            }
        } // todo: handle Mac
    }

    // on Windows expect Phobos to be in "C:\Program Files\LDC x.xx\import" or "C:\tools\ldc2-1.32.2-windows-multilib\import\"
    override fun attachPhobosSources(sdkModificator: SdkModificator, status: SetupStatus) {
        // NOOP: don't need to attach anything if druntime has been found as phobos is in same directory.
        // See attachDruntimeSources() which is attaching both Druntime and Phobos
    }

    /**
     * Neither ldc2 or ldmd2 output config file location to stdout so when using LDC
     * we'll need to try and locate the ldc2.conf file using the same hierarchy that
     * the LDC compiler would use:
     *
     * - The current working directory (todo)
     * - The same directory as the ldc2 executable
     * - The users home directory (Unix: ~/.ldc, Windows: %APPDATA%\.ldc) (todo)
     */
    override fun locateCompilerConfig(sdk: Sdk): String? {
        sdk.homePath?.let {
            val ldcConf = Paths.get(it, compilerConfigFilename).toFile()
            if (ldcConf.exists()) {
                return ldcConf.absolutePath
            }
        }
        LOG.debug("Unable to locate {0} in path: {1}", compilerConfigFilename, sdk.homePath)
        return null
    }

    override fun getIcon(): Icon = DLanguage.Icons.LDC

    override fun getPresentableName(): String = DlangBundle.message("compilers.ldc.presentableName")

    // On a standard Windows installation this should return "C:\Program Files\LDC x.xx\bin"
    // However it could also be a path similar to:
    //  -   "C:\LDC\ldc2-x.xx.x-windows-multilib\bin"
    //  -   "C:\tools\ldc2-x.xx.x-windows-multilib\bin"
    //                  "C:\tools\ldc2-1.32.2-windows-multilib\bin\dub.exe" (LDC has it's own dub)
    //                  "C:\tools\ldc2-1.32.2-windows-multilib\import\" (object.d & all D sources)
    //                  "C:\tools\ldc2-1.32.2-windows-multilib\lib32\ldc2.exe" (phobos & druntime)
    //                  "C:\tools\ldc2-1.32.2-windows-multilib\lib64\ldc2.exe" (phobos & druntime)
    // or for chocolatey based install:
    //  -   "C:\ProgramData\chocolatey\bin"
    // On Linux it should be "/usr/bin" or "/usr/local/bin"
//    override fun suggestHomePath(): String? {
//        return if(SystemInfo.isUnix) {
//            return if (Files.isExecutable(Path.of("/usr/bin", LDC_BINARY_NAME))) "/usr/bin"
//            else if (Files.isExecutable(Path.of("/usr/local/bin", LDC_BINARY_NAME))) "/usr/local/bin"
//            else null
//        }
//            else if(SystemInfo.isWindows) File("C:\\Program Files")
//                .walk(FileWalkDirection.TOP_DOWN)
//                .maxDepth(3)
//                .firstOrNull { it.name == "${LDC_BINARY_NAME}.exe" }?.parent
//            else null
//    }

    override fun isValidSdkHome(sdkHome: String): Boolean {
        val binaryPath = getBinaryFile(sdkHome)
        return binaryPath.exists() && binaryPath.canExecute()
    }

    override fun suggestSdkName(currentSdkName: String?, sdkHome: String): String = presentableName

    /*
    * example output from 'ldc2 --version':
    *
    * <pre>
    * LDC - the LLVM D compiler (1.32.2):
    *  based on DMD v2.102.2 and LLVM 15.0.7
    *  built with LDC - the LLVM D compiler (1.32.2)
    *  Default target: x86_64-pc-windows-msvc
    *  Host CPU: skylake
    *  http://dlang.org - http://wiki.dlang.org/LDC
    * </pre>
    */
    override fun getVersionString(sdk: Sdk): String? = sdk.homePath?.let {getVersionString(it)}

    override fun getVersionString(sdkHome: String): String? {
        val version = getCompilerVersion(sdkHome).get(2_500, TimeUnit.SECONDS)

        val m: Matcher = versionRegexPattern.matcher(version)

        return if (m.matches()) m.group("version") else ""
    }
}
