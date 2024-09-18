package io.github.intellij.dlanguage.psi.scope.processor

import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import io.github.intellij.dlanguage.psi.interfaces.DVisibility
import io.github.intellij.dlanguage.psi.scope.ElementDeclarationHint
import io.github.intellij.dlanguage.utils.AttributeSpecifier

class DVisibilityProcessor : PsiScopeProcessor, ElementDeclarationHint {
    private var result: DVisibility? = null

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element is AttributeSpecifier) {
            if (element.attribute != null) {
                result = if (element.attribute!!.kW_PUBLIC != null) DVisibility.Public
                else if (element.attribute!!.kW_PRIVATE != null) DVisibility.Private
                else if (element.attribute!!.kW_PROTECTED != null) DVisibility.Protected
                else if (element.attribute!!.kW_EXPORT != null) DVisibility.Export
                else if (element.attribute!!.kW_PACKAGE != null) DVisibility.Package(element.attribute!!.identifierChain?.text)
                else null
            }
            if (result != null)
                return false
        }
        return true
    }

    fun getVisibility(): DVisibility? {
        return result
    }

    override fun shouldProcess(kind: ElementDeclarationHint.DeclarationKind): Boolean {
        return kind != ElementDeclarationHint.DeclarationKind.IMPORT
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any?> getHint(hintKey: Key<T?>): T? {
        if (hintKey == ElementDeclarationHint.KEY)
            return this as T
        return super.getHint(hintKey)
    }
}
