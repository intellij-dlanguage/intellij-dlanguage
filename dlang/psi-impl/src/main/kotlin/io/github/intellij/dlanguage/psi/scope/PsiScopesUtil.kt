package io.github.intellij.dlanguage.psi.scope

import com.intellij.openapi.progress.ProgressIndicatorProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiQualifiedReference
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import io.github.intellij.dlanguage.psi.interfaces.Expression
import io.github.intellij.dlanguage.psi.types.DAliasType
import io.github.intellij.dlanguage.psi.types.DArrayType
import io.github.intellij.dlanguage.psi.types.DPointerType
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.psi.types.UserDefinedDType

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

    fun walkChildrenScopes(
        thisElement: PsiElement,
        processor: PsiScopeProcessor,
        state: ResolveState,
        lastParent: PsiElement?,
        place: PsiElement
    ): Boolean {
        var child: PsiElement? = null

        if (lastParent != null && lastParent.parent === thisElement) {
            child = lastParent.prevSibling
            if (child == null) return true // first element
        }
        if (child == null) {
            child = thisElement.lastChild
        }

        while (child != null) {
            if (!child.processDeclarations(processor, state, null, place)) return false
            child = child.prevSibling
        }

        return true
    }

    fun resolveAndWalk(processor: PsiScopeProcessor, reference: PsiQualifiedReference, maxScope: PsiElement?): Boolean {
        return resolveAndWalk(processor, reference, maxScope, false)
    }

    fun resolveAndWalk(processor: PsiScopeProcessor, reference: PsiQualifiedReference, maxScope: PsiElement?, incompleteCode: Boolean): Boolean {
        if (reference.qualifier != null) {
            // composite expression
            var type: DType? = null
            if (reference.qualifier is Expression) {
                type = (reference.qualifier as Expression).dType
                processTypeDeclaration(type, reference.element, processor)
            }
            if (type == null) {
                var target: PsiElement? = reference.qualifier?.reference?.resolve()
                return target?.processDeclarations(processor, ResolveState.initial(), target, reference.element) ?: true
            }
        } else {
            // simple expression -> resolve a top level declaration
            return treeWalkUp(processor, reference.element, maxScope)
        }
        return true
    }

    private fun processTypeDeclaration(type: DType?, place: PsiElement, processor: PsiScopeProcessor) {
        if (type is DPointerType) {
            processTypeDeclaration(type.base, place, processor)
        }
        if (type is DArrayType) {
            processTypeDeclaration(type.base, place, processor)
        }
        if (type is DAliasType) {
            processTypeDeclaration(type.aliased, place, processor)
        }
        if (type is UserDefinedDType) {
            val target = type.resolve()
            target?.processDeclarations(processor, ResolveState.initial(), target, place)
        }
    }
}
