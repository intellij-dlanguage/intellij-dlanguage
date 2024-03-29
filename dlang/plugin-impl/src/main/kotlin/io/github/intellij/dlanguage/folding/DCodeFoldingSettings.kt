package io.github.intellij.dlanguage.folding

import com.intellij.openapi.application.ApplicationManager

abstract class DCodeFoldingSettings {
    abstract var collapsibleOneLineMethods: Boolean

    companion object {
        val instance: DCodeFoldingSettings get() = ApplicationManager.getApplication().getService(DCodeFoldingSettings::class.java)
    }
}
