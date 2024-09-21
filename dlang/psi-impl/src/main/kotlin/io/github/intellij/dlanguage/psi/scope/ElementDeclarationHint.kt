package io.github.intellij.dlanguage.psi.scope

import com.intellij.openapi.util.Key

interface ElementDeclarationHint {

    companion object {
        val KEY = Key.create<ElementDeclarationHint>("ElementDeclarationHint")
    }

    enum class DeclarationKind {
        IMPORT,
        // add more if necessary
    }

    fun shouldProcess(kind: DeclarationKind): Boolean
}
