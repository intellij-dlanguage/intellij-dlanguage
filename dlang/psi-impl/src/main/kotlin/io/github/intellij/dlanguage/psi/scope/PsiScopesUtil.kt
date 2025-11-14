package io.github.intellij.dlanguage.psi.scope

import com.intellij.openapi.progress.ProgressIndicatorProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiQualifiedReference
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import io.github.intellij.dlanguage.psi.interfaces.Expression
import io.github.intellij.dlanguage.psi.named.DLanguageModule
import io.github.intellij.dlanguage.psi.named.DLanguagePackage
import io.github.intellij.dlanguage.psi.scope.processor.PackageOrModuleProcessor
import io.github.intellij.dlanguage.psi.scope.processor.UFCSProcessor
import io.github.intellij.dlanguage.psi.types.*
import io.github.intellij.dlanguage.resolve.IS_QUALIFIED_SYMBOL
import io.github.intellij.dlanguage.utils.AttributeSpecifier

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
            if (!child.processDeclarations(processor, state, lastParent, place)) return false
            child = child.prevSibling
        }

        return true
    }

    /**
     * Look for attribute specifier in previous siblings
     */
    fun searchAttributeSpecifierInPreviousSiblings(
        thisElement: PsiElement,
        processor: PsiScopeProcessor,
        state: ResolveState,
    ): Boolean {
        var child: PsiElement? = thisElement.prevSibling ?: return true

        while (child != null) {
            // ignore attribute with block (example `public {}`) as they don’t impact us
            if (child is AttributeSpecifier && child.declarationBlock == null) {
                if (!processor.execute(child, state))
                    return false
            }
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
                if (!processTypeDeclaration(type, reference.element, processor))
                    return false
            }
            if (type == null) {
                val target: PsiElement? = reference.qualifier?.reference?.resolve()
                var state = ResolveState.initial()
                if (target is DLanguagePackage) {
                    // First try to resolve to a package or module (of imported symbol)
                    val packageProcessor = PackageOrModuleProcessor(processor, target)
                    if (!treeWalkUp(packageProcessor, reference.element, null))
                        return false
                }
                if (target is DLanguagePackage || target is DLanguageModule) {
                    state = state.put(IS_QUALIFIED_SYMBOL, true)
                }
                val `continue` = target?.processDeclarations(processor, state, null, reference.element) ?: true
                if (!`continue`)
                    return false
            }
            // UFCS search
            val processor = UFCSProcessor(processor)
            return treeWalkUp(processor, reference.element, null)
        } else {
            // First try to resolve to a package or module (of imported symbol)
            val packageProcessor = PackageOrModuleProcessor(processor, null)
            if(!treeWalkUp(packageProcessor, reference.element, null))
                return false

            // simple expression -> resolve a top level declaration
            return treeWalkUp(processor, reference.element, maxScope)
        }
    }

    private fun processTypeDeclaration(type: DType?, place: PsiElement, processor: PsiScopeProcessor): Boolean {
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
            return target.processDeclarations(processor, ResolveState.initial(), null, place)
        }
        return true
    }
}
