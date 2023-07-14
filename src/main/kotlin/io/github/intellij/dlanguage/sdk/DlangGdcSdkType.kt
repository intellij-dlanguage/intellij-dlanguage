package io.github.intellij.dlanguage.sdk

import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.SdkModificator
import com.intellij.openapi.util.SystemInfo
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangBundle
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.swing.Icon

class DlangGdcSdkType : DlangDependentSdkType(SDK_TYPE_ID, SDK_NAME, gdcBinaryFilename, defaultBinaryPaths) {
    companion object {
        const val SDK_TYPE_ID = "GDC"
        const val SDK_NAME = "GDC SDK"
        val gdcBinaryFilename = if(SystemInfo.isWindows) "gdc.exe" else "gdc"
        // GDC isn't supported on Windows
        val defaultBinaryPaths = if(SystemInfo.isMac) {
            arrayOf(
                "/usr/local/bin", // dmg install from downloads.dlang.org
                "/opt/homebrew", // installed via Homebrew (when using newer ARM based Mac)
                "/usr/local/opt", // installed via Homebrew (prior to ARM)
                "/opt/local/bin" // installed via MacPorts
            )
        } else {
            // the order of these locations is based on the order of how they typically appear in a users $PATH
            arrayOf(
                "/usr/local/bin",
                "/usr/bin", // Fedora and Arch Linux use this path
                "/snap/bin" // snapcraft.io
            )
        }
        val gdcIcon = DLanguage.Icons.GDC
        val versionRegexPattern = Pattern.compile("gdc\\s\\(GCC\\)\\s(?<version>[\\d.]+)\\s.*")
    }

    override fun getCompilerConfigFilename(): String? = null // not supported by GDC
    override fun attachDruntimeSources(sdkModificator: SdkModificator, status: SetupStatus) {
        // todo: How to handle this for GDC???
        LOG.info("Not attaching Druntime sources for GDC as this has not been implemented yet")
    }

    override fun attachPhobosSources(sdkModificator: SdkModificator, status: SetupStatus) {
        // todo: How to handle this for GDC???
        LOG.info("Not attaching Phobos sources for GDC as this has not been implemented yet")
    }

    override fun locateCompilerConfig(sdk: Sdk): String? = null // not supported by GDC

    override fun getIcon(): Icon = gdcIcon

    override fun getPresentableName(): String = DlangBundle.message("compilers.gdc.presentableName")

    // Windows is not supported
    // On Linux it could be "/usr/bin" or "/usr/local/bin"
    override fun suggestHomePath(): String? {
        return if(SystemInfo.isUnix) "/usr/bin" else null
    }

    override fun isValidSdkHome(sdkHome: String): Boolean {
        val binaryPath = getBinaryFile(sdkHome)
        return binaryPath.exists() && binaryPath.canExecute()
    }

    override fun suggestSdkName(currentSdkName: String?, sdkHome: String): String = presentableName

    /*
    * example output from 'gdc --version':
    *
    * <pre>
    * gdc (GCC) 13.1.1 20230614 (Red Hat 13.1.1-4)
    * Copyright (C) 2023 Free Software Foundation, Inc.
    * This is free software; see the source for copying conditions.  There is NO
    * warranty; not even for MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
    * </pre>
    *
    */
    override fun getVersionString(sdk: Sdk): String? = sdk.homePath?.let {getVersionString(it)}

    override fun getVersionString(sdkHome: String): String? {
        val version = getCompilerVersion(sdkHome).get(2_500, TimeUnit.SECONDS)

        val m: Matcher = versionRegexPattern.matcher(version)

        return if (m.matches()) m.group("version") else ""
    }
}
