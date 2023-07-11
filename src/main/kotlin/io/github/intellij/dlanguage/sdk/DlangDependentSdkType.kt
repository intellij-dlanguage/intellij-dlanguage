package io.github.intellij.dlanguage.sdk

import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.projectRoots.SdkType

import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.DlangSdkType

abstract class DlangDependentSdkType(val id: String) : DlangSdkType(id) {

    override fun getDependencyType(): SdkType = DlangSdkType.getInstance()

    override fun isValidDependency(sdk: Sdk): Boolean = sdk is DlangSdkType

    override fun getUnsatisfiedDependencyMessage(): String = DlangBundle.message("dlang.dependant.sdk.unsatisfied.dependency.message")
}
