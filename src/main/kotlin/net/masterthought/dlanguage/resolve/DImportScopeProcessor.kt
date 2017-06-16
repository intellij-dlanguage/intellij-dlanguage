package net.masterthought.dlanguage.resolve

import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import net.masterthought.dlanguage.utils.Import

/**
 * Created by francis on 6/16/2017.
 */
class DImportScopeProcessor : PsiScopeProcessor {

    var imports = mutableSetOf<Import>()

    override fun handleEvent(event: PsiScopeProcessor.Event, associated: Any?) {
        return
    }

    override fun <T : Any?> getHint(hintKey: Key<T>): T? {
        return null
    }

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element is Import) {
            imports.add(element)
        }
        return true
    }

}
