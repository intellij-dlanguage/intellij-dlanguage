package net.masterthought.dlanguage.processors

import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.psi.interfaces.VariableDeclaration
import net.masterthought.dlanguage.utils.Identifier

/**
 * Created by francis on 6/15/2017.
 */
class DNameScopeProcessor(var start: Identifier) : PsiScopeProcessor {

    val result = mutableSetOf<DNamedElement>()//todo technically should be Declaration

    override fun handleEvent(event: PsiScopeProcessor.Event, associated: Any?) {
        return
    }

    override fun <T : Any?> getHint(hintKey: Key<T>): T? {
        return null
    }

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element is DNamedElement) {
            if (element.name == start.name) {
                if (element is VariableDeclaration) {
                    if (!element.actuallyIsDeclaration()) {
                        return true
                    }
                }
                result.add(element)
                return false
            }
        }
        else{
            throw IllegalStateException()
        }
        return true
    }

}
