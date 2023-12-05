package io.github.intellij.dlanguage.sdk

import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.SdkModificator
import com.intellij.openapi.roots.OrderRootType
import com.intellij.openapi.util.SystemInfo
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangBundle
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.swing.Icon

class DlangGdcSdkType : DlangDependentSdkType(SDK_TYPE_ID, SDK_NAME, GDC_BINARY_NAME) {
    companion object {
        const val SDK_TYPE_ID = "GDC"
        const val SDK_NAME = "GDC SDK"
        const val GDC_BINARY_NAME = "gdc"
    }

    val defaultPhobosAndDruntimePaths = arrayOf(
        // todo: get more clever with this path and take into account the platform arch (also paths on other distros)
        "/usr/lib/gcc/x86_64-redhat-linux/13/include/d", // Fedora (gcc-gdc package in distro repo)
    )

    val versionRegexPattern = Pattern.compile("gdc\\s\\(GCC\\)\\s(?<version>[\\d.]+)\\s.*")

    override fun getCompilerConfigFilename(): String? = null // not supported by GDC
    override fun attachDruntimeSources(sdkModificator: SdkModificator, status: SetupStatus) {
        val compilerSources = firstVirtualFileFrom(defaultPhobosAndDruntimePaths)
        if (compilerSources.isPresent) {
            LOG.info("Attaching both Druntime & Phobos sources for GDC")
            sdkModificator.addRoot(compilerSources.get(), OrderRootType.SOURCES)
            status.runtime = true
            status.phobos = true // both druntime & phobos are in same path
        } else {
            status.runtime = false
            status.phobos = false
        }
    }

    override fun attachPhobosSources(sdkModificator: SdkModificator, status: SetupStatus) {
        // NOOP: don't need to attach anything if druntime has been found as phobos is in same directory.
        // See attachDruntimeSources() which is attaching both Druntime and Phobos
    }

    override fun locateCompilerConfig(sdk: Sdk): String? = null // not supported by GDC

    override fun getIcon(): Icon = DLanguage.Icons.GDC

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
