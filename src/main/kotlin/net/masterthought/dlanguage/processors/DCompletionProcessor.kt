package net.masterthought.dlanguage.processors

import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.stubs.index.DTopLevelDeclarationsByModule
import net.masterthought.dlanguage.utils.SingleImport

/**
 * Created by francis on 6/17/2017.
 */

class DCompletionProcessor : PsiScopeProcessor {

    val completions: MutableSet<String> = mutableSetOf()//todo a list might improve performance

    override fun handleEvent(event: PsiScopeProcessor.Event, associated: Any?) {
        return
    }

    override fun <T : Any?> getHint(hintKey: Key<T>): T? {
        return null
    }

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element is DNamedElement) {
            completions.add(element.name)
        }
        if(element is SingleImport){
            completions.addAll(DTopLevelDeclarationsByModule.getSymbolsFromImport(element).map { it.name })
        }
        return true
    }


}
