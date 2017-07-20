package net.masterthought.dlanguage.processors

import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.psi.interfaces.Declaration

/**
 * Created by francis on 6/17/2017.
 */

class DCompletionProcessor : PsiScopeProcessor {

    val completions: MutableSet<String> = mutableSetOf()

    override fun handleEvent(event: PsiScopeProcessor.Event, associated: Any?) {
        return
    }

    override fun <T : Any?> getHint(hintKey: Key<T>): T? {
        return null
    }

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element is Declaration && element is DNamedElement) {
            completions.add(element.name)
        }
        return true
    }

}
