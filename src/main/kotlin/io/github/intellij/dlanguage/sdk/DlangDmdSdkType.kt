package io.github.intellij.dlanguage.sdk

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.execution.process.ProcessOutput
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.SdkModel
import com.intellij.openapi.projectRoots.SdkModificator
import com.intellij.openapi.roots.OrderRootType
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.vfs.LocalFileSystem
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangBundle
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Files
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.swing.Icon

class DlangDmdSdkType : DlangDependentSdkType(SDK_TYPE_ID, SDK_NAME, dmdBinaryFilename, defaultBinaryPaths) {
    companion object {
        const val SDK_TYPE_ID = "DMD"
        const val SDK_NAME = "DMD v2 SDK"
        val dmdBinaryFilename = if(SystemInfo.isWindows) "dmd.exe" else "dmd"
        val defaultBinaryPaths = if(SystemInfo.isWindows) {
            arrayOf(
                "C:/D/dmd2/windows/bin64", // default to the 64-bit compiler
                "C:/D/dmd2/windows/bin"
            )
        } else if(SystemInfo.isMac) {
            arrayOf(
                "/usr/local/bin", // dmg install from downloads.dlang.org
                "/opt/homebrew", // installed via Homebrew (when using newer ARM based Mac)
                "/usr/local/opt", // installed via Homebrew (prior to ARM)
                "/opt/local/bin" // installed via MacPorts
            )
        } else {
            // the order of these locations is based on the order of how they typically appear in a users $PATH
            arrayOf(
                "/usr/local/bin", // Ubuntu package is installed here
                "/usr/bin", // Fedora (official .rpm) and Arch Linux use this path
                "/snap/bin" // snapcraft.io (/snap/bin/dmd is a symlink to /snap/dmd/current/bin/dmd)
            )
        }

        val defaultPhobosPaths = if(SystemInfo.isWindows) {
            arrayOf("C:/D/dmd2/src/phobos")
        } else if(SystemInfo.isMac) {
            arrayOf("/Library/D/dmd/src/phobos") // installed via Homebrew
        } else {
            arrayOf(
                "/usr/include/dmd/phobos", // Fedora (official .rpm)
                "/usr/local/include/dmd/phobos", // Ubuntu (should it be src/phobos?)
                "/usr/include/dlang/dmd", // Arch Linux uses non-standard directory structure (see: #457 and #743)
                "/snap/dmd/current/import/phobos" // snapcraft.io
            )
        }

        val defaultDruntimePaths = if (SystemInfo.isWindows) {
            arrayOf("C:/D/dmd2/src/druntime/import")
        } else if(SystemInfo.isMac) {
            arrayOf("/Library/D/dmd/src/druntime/import") // installed via Homebrew
        } else {
            arrayOf(
                "/usr/include/dmd/druntime/import", // Fedora (official .rpm)
                "/usr/local/include/dmd/druntime/import", // Ubuntu (should it be src/druntime/import?)
                "/usr/include/dlang/dmd", // Arch Linux uses non-standard directory structure (see: #457 and #743)
                "/snap/dmd/current/import/druntime" // snapcraft.io
            )
        }

        val dmdIcon = DLanguage.Icons.DMD
        val versionRegexPattern = Pattern.compile("DMD.*\\sv(?<version>[\\d.]+)")
    }

    override fun getCompilerConfigFilename(): String = if(SystemInfo.isWindows) "sc.ini" else "dmd.conf"
    override fun attachDruntimeSources(sdkModificator: SdkModificator, status: SetupStatus) {
        val phobosSource = firstVirtualFileFrom(defaultDruntimePaths)
        if (phobosSource.isPresent) {
            sdkModificator.addRoot(phobosSource.get(), OrderRootType.SOURCES)
            status.phobos = true
        } else {
            status.phobos = false
        }
    }

    override fun attachPhobosSources(sdkModificator: SdkModificator, status: SetupStatus) {
        val phobosSource = firstVirtualFileFrom(defaultPhobosPaths)
        if (phobosSource.isPresent) {
            sdkModificator.addRoot(phobosSource.get(), OrderRootType.SOURCES)
            status.phobos = true
        } else {
            status.phobos = false
        }
    }

    /**
     * Only dmd supports displaying location of the config file by invoking binary without any args
     */
    override fun locateCompilerConfig(sdk: Sdk): String? {
        sdk.homePath?.let {
            val cmd = GeneralCommandLine(this.getBinaryFile(it).absolutePath)

            return ApplicationManager.getApplication()
                .executeOnPooledThread<ProcessOutput> {
                    CapturingProcessHandler(
                        cmd.createProcess(),
                        Charset.defaultCharset(),
                        cmd.commandLineString
                    )
                        .runProcess(3_000)
                }.get()
                .stdoutLines
                .filter { line -> line.startsWith("Config file:") }
                .map { line -> line.replace("Config file: ", "").trim() }
                .firstOrNull()
        }
        LOG.debug("Unable to locate {0} by invoking {1}", compilerConfigFilename, dmdBinaryFilename)
        return null
    }

    override fun getIcon(): Icon = dmdIcon

    override fun getPresentableName(): String = DlangBundle.message("compilers.dmd.presentableName")

    //override fun getVersionString(sdk: Sdk): String? = null // todo

    // this is exactly what super class does
    override fun setupSdkPaths(sdk: Sdk, sdkModel: SdkModel): Boolean {
        setupSdkPaths(sdk)
        return true
    }

    /**
     * Windows has docs in 'C:\D\dmd2\html\d' and sources in ['C:\D\dmd2\src\phobos',
     * 'C:\D\dmd2\src\druntime\import'] OSX has docs in ??? and sources in
     * ['/Library/D/dmd/src/phobos', '/Library/D/dmd/src/druntime/import'] Linux has docs in
     * '/usr/share/dmd/html/d' and sources in ['/usr/include/dmd/phobos',
     * '/usr/include/dmd/druntime/import']
     *
     * @param sdk The DMD installation
     */
    override fun setupSdkPaths(sdk: Sdk) {
        val sdkModificator = sdk.sdkModificator

        var status = SetupStatus(false, false, false)

        if (SystemInfo.isWindows) {
            status = setupSDKPathsFromWindowsConfigFile(sdk, sdkModificator)
        }

        // documentation paths (todo: find out why using 'OrderRootType.DOCUMENTATION' didn't work)
        if (!status.documentation) {
            setupDocumentationPath(sdk, sdkModificator, status)
        }

        // add phobos to sources root
        if (!status.phobos) {
            attachPhobosSources(sdkModificator, status)
        }

        // add druntime to sources root
        if (!status.runtime) {
            attachDruntimeSources(sdkModificator, status)
        }

        sdkModificator.commitChanges()
    }

    /*
    * example output from 'dmd --version':
    *
    * <pre>
    * DMD64 D Compiler v2.104.0
    * Copyright (C) 1999-2023 by The D Language Foundation, All Rights Reserved written by Walter Bright
    * </pre>
    */
    override fun getVersionString(sdk: Sdk): String? = sdk.homePath?.let {getVersionString(it)}

    override fun getVersionString(sdkHome: String): String? {
        val version = getCompilerVersion(sdkHome).get(2_500, TimeUnit.SECONDS)

        val m: Matcher = versionRegexPattern.matcher(version)

        return if (m.matches()) m.group("version") else ""
    }

    // todo: overhaul this method (also worth extracting to another class) to support both Windows and Unix
    private fun setupSDKPathsFromWindowsConfigFile(sdk: Sdk, sdkModificator: SdkModificator): SetupStatus {
        return try {
            val configFile = Optional.ofNullable(locateCompilerConfig(sdk))
            if (configFile.isEmpty) {
                return SetupStatus(false, false, false)
            }
            val file = File(configFile.get())
            if (!file.exists()) {
                return SetupStatus(false, false, false)
            }
            //DFLAGS="-I%@P%\..\..\src\phobos" "-I%@P%\..\..\src\druntime\import"
            val phobos = arrayOfNulls<String>(1)
            val phobosPattern = Pattern
                .compile("\"-I%@P%([\\.\\\\A-Za-z]+phobos[\\.\\\\A-Za-z]*)\"")
            val druntime = arrayOfNulls<String>(1)
            val druntimePattern = Pattern
                .compile("\"-I%@P%([\\.\\\\A-Za-z]+druntime\\\\import[\\.\\\\A-Za-z]*)\"")
            Files.lines(file.toPath()).forEach { line: String ->
                if (line.contains("DFLAGS=")) {
                    val phobosMatcher = phobosPattern.matcher(line)
                    val druntimeMatcher = druntimePattern.matcher(line)
                    if (phobosMatcher.find()) {
                        phobos[0] = phobosMatcher.group(1)
                    }
                    if (druntimeMatcher.find()) {
                        druntime[0] = druntimeMatcher.group(1)
                    }
                }
            }

            // todo: handle variations in these paths as following code could break
            val phobosFile = File(File(getDlangCompilerPath(sdk)).getParent() + phobos[0])
            val druntimeFile = File(File(getDlangCompilerPath(sdk)).getParent() + druntime[0])

            if (phobosFile.exists() && druntimeFile.exists()) {
                val phobosVirtualFile = LocalFileSystem.getInstance().findFileByPath(phobosFile.absolutePath)
                val druntimeVirtualFile = LocalFileSystem.getInstance().findFileByPath(druntimeFile.absolutePath)
                if (phobosVirtualFile == null || druntimeVirtualFile == null) return SetupStatus(false, false, false)
                sdkModificator.addRoot(phobosVirtualFile, OrderRootType.SOURCES)
                sdkModificator.addRoot(druntimeVirtualFile, OrderRootType.SOURCES)
                SetupStatus(true, true, false)
            } else {
                SetupStatus(false, false, false)
            }
        } catch (e: IOException) {
            LOG.error(e)
            SetupStatus(false, false, false)
        }
    }
}
