package io.github.intellij.dlanguage.processors

import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import io.github.intellij.dlanguage.utils.SingleImport

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

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element is SingleImport) {
            imports.add(element)
        }
        return true
    }

}
