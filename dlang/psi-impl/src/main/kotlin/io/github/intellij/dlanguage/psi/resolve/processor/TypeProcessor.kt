package io.github.intellij.dlanguage.psi.resolve.processor

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveResult
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.SmartList
import com.intellij.util.containers.toArray
import io.github.intellij.dlanguage.psi.DResolveResult
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.interfaces.TemplateParameter
import io.github.intellij.dlanguage.psi.interfaces.UserDefinedType
import io.github.intellij.dlanguage.utils.AliasInitializer
import io.github.intellij.dlanguage.utils.DeclaratorIdentifier
import io.github.intellij.dlanguage.utils.TemplateDeclaration
import io.github.intellij.dlanguage.utils.TemplateParameters

class TypeProcessor(private val elementName: String,
                    private val startPLace: PsiElement,
                    private val forceTemplateSearch: Boolean) : PsiScopeProcessor {
    private var candidates: MutableList<DResolveResult>? = null
    private var resolveResult = ResolveResult.EMPTY_ARRAY

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element !is UserDefinedType && element !is DeclaratorIdentifier &&
            element !is AliasInitializer && element !is TemplateDeclaration &&
            element !is TemplateParameter) {
            return true
        }
        if ((element as DNamedElement).name != elementName) return true
        if (forceTemplateSearch &&
            element !is TemplateDeclaration && PsiTreeUtil.getChildOfType(element, TemplateParameters::class.java) == null) {
            return true
        }
        candidates = SmartList()
        (candidates as SmartList<DResolveResult>).add(DResolveResult(element))
        resolveResult = null
        return false
    }

    fun getResult(): Array<ResolveResult> {
        if (resolveResult != null) return resolveResult
        if (candidates == null) {
            resolveResult = ResolveResult.EMPTY_ARRAY
            return resolveResult
        }
        resolveResult = candidates!!.toArray(ResolveResult.EMPTY_ARRAY)
        return resolveResult
    }
}
