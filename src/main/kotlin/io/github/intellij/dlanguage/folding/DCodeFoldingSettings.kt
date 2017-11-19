package io.github.intellij.dlanguage.folding

import com.intellij.openapi.components.ServiceManager

abstract class DCodeFoldingSettings {
    abstract var collapsibleOneLineMethods: Boolean

    companion object {
        val instance: DCodeFoldingSettings get() = ServiceManager.getService(DCodeFoldingSettings::class.java)
    }
}
