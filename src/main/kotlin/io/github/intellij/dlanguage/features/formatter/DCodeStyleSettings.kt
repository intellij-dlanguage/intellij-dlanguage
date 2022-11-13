package io.github.intellij.dlanguage.features.formatter

import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CustomCodeStyleSettings

@Suppress("PropertyName")
class DCodeStyleSettings(container: CodeStyleSettings) : CustomCodeStyleSettings(DCodeStyleSettings::class.java.simpleName, container) {

    @JvmField
    var SPACE_BEFORE_IMPORT_BINDS_COLON: Boolean = true
}
