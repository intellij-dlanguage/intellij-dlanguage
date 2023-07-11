package io.github.intellij.dlanguage.sdk

/**
 * Keeps track of whether the D compiler has all features configured.
 * Ported over from Java code originally in DlangSdkType
 */
data class SetupStatus(
    var runtime: Boolean = false,
    var phobos: Boolean = false,
    var documentation: Boolean = false
)
