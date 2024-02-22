package io.github.intellij.dlanguage.sdk

import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.SdkModel
import com.intellij.openapi.projectRoots.SdkType
import com.intellij.openapi.util.SystemInfo

import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.DlangSdkType
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

abstract class DlangDependentSdkType(
    id: String,
    name: String,
    compilerBinaryFilename: String,
) : DlangSdkType(id, compilerBinaryFilename) {

    override fun getDependencyType(): SdkType = DlangSdkType.getInstance()

    override fun isValidDependency(sdk: Sdk): Boolean = sdk is DlangSdkType

    override fun getUnsatisfiedDependencyMessage(): String = DlangBundle.message("dlang.dependant.sdk.unsatisfied.dependency.message")

    /**
     * Attempts to locate the relevant D compiler on the users system based on known install locations
     * @return the parent directory of the D compiler binary
     */
    override fun suggestHomePath(): String? {
        return if(SystemInfo.isMac) {
            return if (Files.isExecutable(Path.of("/opt/homebrew", compilerBinaryFilename))) "/opt/homebrew" // installed via Homebrew (when using newer ARM based Mac)
            else if (Files.isExecutable(Path.of("/usr/local/opt", compilerBinaryFilename))) "/usr/local/opt" // installed via Homebrew (prior to ARM)
            else if (Files.isExecutable(Path.of("/opt/local/bin", compilerBinaryFilename))) "/opt/local/bin" // installed via MacPorts
            else null
        }
        else if(SystemInfo.isUnix) {
            // mostly Linux but could still be Mac (dmg install) or BSD (except for snapcraft)
            return if (Files.isExecutable(Path.of("/usr/bin", compilerBinaryFilename))) "/usr/bin"
            else if (Files.isExecutable(Path.of("/usr/local/bin", compilerBinaryFilename))) "/usr/local/bin"
            else if (Files.isExecutable(Path.of("/snap/bin", compilerBinaryFilename))) "/snap/bin"
            else null
        }
        else if(SystemInfo.isWindows) {
            val chocoInstalledBinary = File("C:\\ProgramData\\chocolatey\\bin")
                .walk(FileWalkDirection.TOP_DOWN)
                .maxDepth(3)
                .firstOrNull { it.name == "${compilerBinaryFilename}.exe" }

            return chocoInstalledBinary?.parent ?: File("C:\\Program Files")
                .walk(FileWalkDirection.TOP_DOWN)
                .maxDepth(3)
                .firstOrNull { it.name == "${compilerBinaryFilename}.exe" }?.parent
        }
        else null
    }

    override fun setupSdkPaths(sdk: Sdk, sdkModel: SdkModel): Boolean {
        setupSdkPaths(sdk)
        sdkModel.addSdk(sdk)
        return true
    }

    // This should be overridden in DlangDmdSdkType.kt and DlangLdcSdkType.kt to handle reading paths from config file
    override fun setupSdkPaths(sdk: Sdk) {
        val sdkModificator = sdk.sdkModificator
        val status = SetupStatus(false, false, false)

        attachDruntimeSources(sdkModificator, status)
        attachPhobosSources(sdkModificator, status)
        //setupDocumentationPath(sdk, sdkModificator, status) todo: find out why using 'OrderRootType.DOCUMENTATION' didn't work

        sdkModificator.commitChanges()
    }
}
