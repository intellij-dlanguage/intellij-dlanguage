package io.github.intellij.dlanguage.sdk

import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.util.SystemInfo
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangBundle
import java.nio.file.Paths
import javax.swing.Icon

class DlangGdcSdkType : DlangDependentSdkType(SDK_TYPE_ID) {
    companion object {
        const val SDK_TYPE_ID = "GDC"
        const val SDK_NAME = "GDC SDK"
        val gdcBinary = if(SystemInfo.isWindows) "gdc.exe" else "gdc"
        val gdcIcon = DLanguage.Icons.GDC
    }

    override fun getIcon(): Icon = gdcIcon

    override fun getPresentableName(): String = DlangBundle.message("compilers.gdc.presentableName")

    // Windows is not supported
    // On Linux it could be "/usr/bin" or "/usr/local/bin"
    override fun suggestHomePath(): String? {
        return if(SystemInfo.isUnix) "/usr/bin" else null
    }

    override fun isValidSdkHome(sdkHome: String): Boolean {
        val binaryPath = Paths.get(sdkHome, gdcBinary).toFile()
        return binaryPath.exists() && binaryPath.canExecute()
    }

    override fun suggestSdkName(currentSdkName: String?, sdkHome: String): String = presentableName

    override fun getVersionString(sdk: Sdk): String? = null // gdc just displays the gcc version which isn't really useful
}
