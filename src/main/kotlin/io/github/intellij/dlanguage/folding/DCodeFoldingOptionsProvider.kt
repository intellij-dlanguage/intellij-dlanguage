package io.github.intellij.dlanguage.folding

import com.intellij.application.options.editor.CodeFoldingOptionsProvider
import com.intellij.openapi.options.BeanConfigurable

class DCodeFoldingOptionsProvider :
    BeanConfigurable<DCodeFoldingSettings>(DCodeFoldingSettings.instance),
    CodeFoldingOptionsProvider {

    init {
        val settings = instance!!
        val getter: () -> Boolean = { settings.collapsibleOneLineMethods }
        val setter: (Boolean) -> Unit = { v -> settings.collapsibleOneLineMethods = v }

        checkBox("D one-line methods", getter, setter)
    }
}
