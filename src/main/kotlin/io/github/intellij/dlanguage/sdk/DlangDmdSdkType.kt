package io.github.intellij.dlanguage.sdk

import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.SdkModel
import com.intellij.openapi.util.SystemInfo
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangBundle
import java.io.File
import java.nio.file.Paths
import javax.swing.Icon

class DlangDmdSdkType : DlangDependentSdkType(SDK_TYPE_ID) {
    companion object {
        const val SDK_TYPE_ID = "DMD"
        const val SDK_NAME = "DMD v2 SDK"
        val dmdBinary = if(SystemInfo.isWindows) "dmd.exe" else "dmd"
        val dmdIcon = DLanguage.Icons.DMD
    }

    override fun getIcon(): Icon = dmdIcon

    override fun getPresentableName(): String = DlangBundle.message("compilers.dmd.presentableName")

    // todo: move code from old Java file
//    override fun suggestHomePath(): String? {
//        return if(SystemInfo.isUnix) "/usr/bin" else null
//    }

    /**
     * When user set up DMD SDK path this method checks if specified path contains DMD compiler executable.
     *
     * This method determines if it can run the dmd executable based on a home path that's passed in. So in the case
     * that the sdk home is "C:\D\dmd2\" then this method would return true if "C:\D\dmd2\windows\bin\dmd.exe" exists
     * and is executable.
     *
     * @param sdkHome path to the root directory of a dmd installation
     * @return true if the sdk home contains a executable dmd compiler
     */
    override fun isValidSdkHome(sdkHome: String): Boolean {
        val dmdExe = if(SystemInfo.isWindows && File(sdkHome).isDirectory) {
            // default dmd location on Windows is: "C:\D\dmd2\windows\bin\dmd.exe"
            Paths.get(sdkHome, "windows", "bin", dmdBinary).toFile()
        } else {
            Paths.get(sdkHome, dmdBinary).toFile()
        }

        return dmdExe.exists() && dmdExe.canExecute()
    }

    //override fun suggestSdkName(currentSdkName: String?, sdkHome: String): String = // todo

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
        if (!status.getDocumentationStatus()) {
            setupDocumentationPath(sdk, sdkModificator, status)
        }

        // add phobos to sources root
        if (!status.getPhobosStatus()) {
            setupPhobosPaths(sdkModificator, status)
        }

        // add druntime to sources root
        if (!status.getRuntimeStatus()) {
            setupRuntimePaths(sdkModificator, status)
        }

        sdkModificator.commitChanges()
    }
}
