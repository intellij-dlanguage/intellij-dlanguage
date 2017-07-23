package net.masterthought.dlanguage.processors

import com.intellij.openapi.util.Key
import com.intellij.psi.scope.PsiScopeProcessor

interface DResolveProcessor<Call, Declaration> : PsiScopeProcessor {
    fun matches(call: Call, decl: Declaration): Boolean
    val result: MutableSet<Declaration>
    override fun handleEvent(event: PsiScopeProcessor.Event, associated: Any?) {
        return
    }

    override fun <T : Any?> getHint(hintKey: Key<T>): T? {
        return null
    }
}
