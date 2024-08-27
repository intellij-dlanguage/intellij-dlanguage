package io.github.intellij.dlanguage.resolve.processors

import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.scope.PsiScopeProcessor
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement

interface DResolveProcessor<Call : PsiElement, Declaration : DNamedElement> : PsiScopeProcessor {
    fun matches(call: Call, decl: Declaration): Boolean
    val result: MutableSet<Declaration>
    override fun handleEvent(event: PsiScopeProcessor.Event, associated: Any?) {
        return
    }

    override fun <T : Any?> getHint(hintKey: Key<T>): T? {
        return null
    }
}
