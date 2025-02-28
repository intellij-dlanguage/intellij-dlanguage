package io.github.intellij.dlanguage.resolve

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.interfaces.Declaration
import io.github.intellij.dlanguage.utils.*

/**
 * Created by francis on 6/15/2017.
 */

object ScopeProcessorImplUtil {

    fun processDeclaration(def: Declaration,
                           processor: PsiScopeProcessor,
                           state: ResolveState,
                           lastParent: PsiElement?,
                           place: PsiElement): Boolean {
        when (def) {
            is AliasInitializer ->
                return processor.execute(def, state)
            is AliasDeclaration -> {
                // the element is in the alias declaration, so it can’t match to this declaration
                if (lastParent == def)
                    return true

                if (def.aliasInitializers.isNotEmpty()) {
                    for (varDeclaration in def.aliasInitializers) {
                        if (!processor.execute(varDeclaration, state))
                            return false
                    }
                    return true
                }
                if (def.declaratorIdentifiers.isNotEmpty()) {
                    for (varDeclaration in def.declaratorIdentifiers) {
                        if (!processor.execute(varDeclaration, state))
                            return false
                    }
                    return true
                }
            }
            is AnonymousEnumDeclaration -> {
                for (enumMember in def.enumMembers) {
                    if (!processor.execute(enumMember, state)) {
                        return false
                    }
                }
                return true
            }
            is ClassDeclaration,
            is EnumDeclaration,
            is FunctionDeclaration,
            is InterfaceDeclaration,
            is VersionSpecification
                -> return processor.execute(def, state)
            is DeclarationStatement ->
                return !(def.declaration != null && !processor.execute(def.declaration!!, state))
            is StructDeclaration,
            is UnionDeclaration -> {
                return if ((def as DNamedElement).nameIdentifier == null)
                    def.processDeclarations(processor, state, lastParent, place)
                else {
                    processor.execute(def, state)
                }
            }
            is ConditionalDeclaration,
            is ImportDeclaration -> {
                return def.processDeclarations(processor, state, lastParent, place)
            }
            is TemplateMixinDeclaration -> {
                var toContinue = true
                for (declaration in def.declarations) {
                    if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                        toContinue = false
                    }
                }
                return toContinue
            }
            is TemplateDeclaration -> {
                var toContinue = true
                for (declaration in def.declarations) {
                    if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                        toContinue = false
                    }
                }
                if (!processor.execute(def, state)) {
                    return false
                }
                return toContinue
            }
            is DeclarationBlock,
            is AttributeSpecifier,
            is AutoDeclaration,
            is SpecifiedVariableDeclaration -> {
                return def.processDeclarations(processor, state, lastParent, place)
            }
            else -> return true
        }
        return true
    }


    @Suppress("UNUSED_PARAMETER")
    fun processParameters(element: Parameters,
                          processor: PsiScopeProcessor,
                          state: ResolveState,
                          lastParent: PsiElement?,
                          place: PsiElement): Boolean {
        for (p in element.parameters) {
            if (!processor.execute(p, state)) {
                return false
            }
        }
        return true
    }

}
