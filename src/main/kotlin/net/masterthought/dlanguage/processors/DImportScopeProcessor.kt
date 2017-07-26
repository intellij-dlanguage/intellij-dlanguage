package net.masterthought.dlanguage.processors

import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import net.masterthought.dlanguage.resolve.processors.parameters.DAttributesFinder
import net.masterthought.dlanguage.utils.SingleImport

/**
 * Created by francis on 6/16/2017.
 */
class DImportScopeProcessor : PsiScopeProcessor {

    val imports = mutableSetOf<SingleImport>()
    val publicImports = mutableSetOf<SingleImport>()

    override fun handleEvent(event: PsiScopeProcessor.Event, associated: Any?) {
        return
    }

    override fun <T : Any?> getHint(hintKey: Key<T>): T? {
        return null
    }

    //todo for getting public imports this scope processor is not necessary and stub indexes should be used instead
    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element is SingleImport) {
            val finder = DAttributesFinder(element.identifierChain!!)
            finder.recurseUp()
            if (finder.isPublic()) {
                publicImports.add(element)
            }
            imports.add(element)
        }
        return true
    }

}
