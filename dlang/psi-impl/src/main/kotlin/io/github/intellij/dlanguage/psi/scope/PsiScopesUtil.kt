package io.github.intellij.dlanguage.psi.scope

import com.intellij.openapi.progress.ProgressIndicatorProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiQualifiedReference
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor

object PsiScopesUtil {
    fun treeWalkUp(processor: PsiScopeProcessor,
                   entrance: PsiElement,
                   maxScope: PsiElement?): Boolean {
        var scope: PsiElement? = entrance
        var prevParent: PsiElement? = entrance
        while (scope != null) {
            ProgressIndicatorProvider.checkCanceled()
            if (!scope.processDeclarations(processor, ResolveState.initial(), prevParent, entrance)) {
                return false
            }
            if (scope == maxScope) break
            prevParent = scope
            scope = scope.context
        }
        return true
    }

    fun resolveAndWalk(processor: PsiScopeProcessor, reference: PsiQualifiedReference, maxScope: PsiElement?): Boolean {
        return resolveAndWalk(processor, reference, maxScope, false)
    }

    fun resolveAndWalk(processor: PsiScopeProcessor, reference: PsiQualifiedReference, maxScope: PsiElement?, incompleteCode: Boolean): Boolean {
        return treeWalkUp(processor, reference.element, maxScope)
    }
}
