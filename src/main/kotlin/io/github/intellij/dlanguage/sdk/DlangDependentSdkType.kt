package io.github.intellij.dlanguage.sdk

import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.SdkModel
import com.intellij.openapi.projectRoots.SdkType

import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.DlangSdkType

abstract class DlangDependentSdkType(
    id: String,
    name: String,
    compilerBinaryFilename: String,
    defaultBinaryPaths: Array<String>
) : DlangSdkType(id, compilerBinaryFilename, defaultBinaryPaths) {

    override fun getDependencyType(): SdkType = DlangSdkType.getInstance()

    override fun isValidDependency(sdk: Sdk): Boolean = sdk is DlangSdkType

    override fun getUnsatisfiedDependencyMessage(): String = DlangBundle.message("dlang.dependant.sdk.unsatisfied.dependency.message")

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
