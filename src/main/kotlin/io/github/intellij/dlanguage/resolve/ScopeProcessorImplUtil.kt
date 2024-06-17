package io.github.intellij.dlanguage.resolve

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
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
        var toContinue = true
        if (def is AliasInitializer) {
            return processor.execute(def, state)
        }
        if (def is AliasDeclaration) {
            if (def.aliasInitializers.isNotEmpty()) {
                for (varDeclaration in def.aliasInitializers) {
                    toContinue = processor.execute(varDeclaration, state)
                }
                return toContinue
            }
            if (def.declaratorIdentifiers.isNotEmpty()) {
                for (varDeclaration in def.declaratorIdentifiers) {
                    toContinue = processor.execute(varDeclaration, state)
                }
                return toContinue
            }
        }
        if (def is AliasThisDeclaration) {
            return true
        }
        if ((def as? ClassDeclaration)?.structBody?.declarations != null) {
            for (declaration in def.structBody!!.declarations) {
                if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
            if (!processor.execute(def, state)) {
                return false
            }
            return toContinue
        }
        if (def is ConditionalDeclaration) {
            return processConditionalDeclaration(def, processor, state, lastParent, place)
        }
        if (def is Constructor) {
            return processor.execute(def, state)
        }
        if (def is Destructor) {

        }
        if (def is EnumDeclaration) {
            if (def.identifier == null && def.enumBody?.enumMembers != null) {
                for (enumMember in def.enumBody!!.enumMembers) {
                    if (!processor.execute(enumMember, state)) {
                        toContinue = false
                    }
                }
            }
            if (!processor.execute(def, state)) {
                toContinue = false
            }
            return toContinue
        }
        if (def is AnonymousEnumDeclaration) {
            for (enumMember in def.enumMembers) {
                if (!processor.execute(enumMember, state)) {
                    toContinue = false
                }
            }
            return toContinue
        }
        if (def is FunctionDeclaration) {
            return processor.execute(def, state)
        }
        if (def is ImportDeclaration) {
            for (import in def.singleImports) {
                if (!processor.execute(import, state)) {
                    toContinue = false
                }
            }
            return toContinue
        }
        if (def is InterfaceDeclaration) {
            if (def.structBody != null) {
                for (d in def.structBody!!.declarations) {
                    if (!processDeclaration(d, processor, state, lastParent, place)) {
                        toContinue = false
                    }
                }
            }
            if (!processor.execute(def, state)) {
                toContinue = false
            }
            return toContinue
        }
        if (def is MixinDeclaration) {

        }
        if (def is MixinTemplateDeclaration) {
            for (declaration in def.templateDeclaration!!.declarations) {
                if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
            if (!processor.execute(def.templateDeclaration!!, state)) {
                return false
            }
            return toContinue
        }
        if (def is SharedStaticConstructor) {

        }
        if (def is SharedStaticDestructor) {

        }
        if (def is StaticAssertDeclaration) {

        }
        if (def is StaticConstructor) {

        }
        if (def is StaticDestructor) {

        }
        if (def is StructDeclaration) {
            if (def.structBody?.declarations != null) {
                for (declaration in def.structBody!!.declarations) {
                    if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                        toContinue = false
                    }
                }
            }
            if (!processor.execute(def, state)) {
                return false
            }
            return toContinue
        }
        if (def is TemplateDeclaration) {
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
        if (def is UnionDeclaration) {
            if (def.structBody?.declarations != null) {
                for (declaration in def.structBody!!.declarations) {
                    if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                        toContinue = false
                    }
                }
            }
            if (!processor.execute(def, state)) {
                return false
            }
            return toContinue
        }
        if (def is Unittest) {

        }
        if (def is AutoDeclaration) {
            for (initializer in def.autoAssignments) {
                if (!processor.execute(initializer, state)) {
                    toContinue = false
                }
            }
            return toContinue
        }
        if (def is SpecifiedVariableDeclaration) {
            for (declarator in def.identifierInitializers) {
                if (!processor.execute(declarator, state)) {
                    toContinue = false
                }
            }
            return toContinue
        }
        if (def is DeclarationStatement) {
            if (def.declaration != null && !processor.execute(def.declaration!!, state)) {
                toContinue = false
            }
            return toContinue
        }
        if (def is AttributeSpecifier) {

        }
        if (def is Invariant) {

        }
        if (def is VersionSpecification) {
            if (!processor.execute(def, state)) {
                toContinue = false
            }
        }
        if (def is DebugSpecification) {
        }
        return toContinue
    }

    fun processConditionalDeclaration(element: ConditionalDeclaration,
                                      processor: PsiScopeProcessor,
                                      state: ResolveState,
                                      lastParent: PsiElement?,
                                      place: PsiElement): Boolean {

        var defNotFound = true
        for (declaration in element.declarations) {
            if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                defNotFound = false
            }
        }
        return defNotFound
    }


    fun processConditionalStatement(element: ConditionalStatement,
                                    processor: PsiScopeProcessor,
                                    state: ResolveState,
                                    lastParent: PsiElement?,
                                    place: PsiElement): Boolean {
        //always recurse in since this is a compile time condition
        //handle place and lastParent
        var toContinue = true
        for (dLangStatement in element.statements) {
            if (!dLangStatement.processDeclarations(processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        return toContinue
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

    @Suppress("UNUSED_PARAMETER")
    fun processTemplateParameters(element: TemplateParameters,
                                  processor: PsiScopeProcessor,
                                  state: ResolveState,
                                  lastParent: PsiElement?,
                                  place: PsiElement): Boolean {
        for (p in element.templateParameterList?.templateParameters ?: listOf()) {
            if (!processor.execute(p, state)) {
                return false
            }
        }
        return true
    }

}
