package net.masterthought.dlanguage.resolve

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import net.masterthought.dlanguage.utils.*

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
        if (def.aliasDeclaration?.aliasInitializers != null) {
            for (varDeclaration in def.aliasDeclaration?.aliasInitializers!!) {
                toContinue = processor.execute(varDeclaration, state)
            }
            return toContinue
        }
        if (def.aliasThisDeclaration != null) {
            return true
        }
        if (def.classDeclaration?.interfaceOrClass?.structBody?.declarations != null) {
            for (declaration in def.classDeclaration!!.interfaceOrClass!!.structBody!!.declarations) {
                if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
            if (!processor.execute(def.classDeclaration!!.interfaceOrClass!!, state)) {
                return false
            }
            return toContinue
        }
        if (def.conditionalDeclaration != null) {
            return ScopeProcessorImplUtil.processConditionalDeclaration(def.conditionalDeclaration!!, processor, state, lastParent, place)
        }
        if (def.constructor != null) {
            return processor.execute(def.constructor!!, state)
        }
        if (def.destructor != null) {

        }
        if (def.enumDeclaration != null) {
            if (def.enumDeclaration?.enumBody?.enumMembers != null) {
                for (enumMember in def.enumDeclaration!!.enumBody!!.enumMembers) {
                    if (!processor.execute(enumMember, state)) {
                        toContinue = false
                    }
                }
            }
            if (!processor.execute(def.enumDeclaration!!, state)) {
                toContinue = false
            }
            return toContinue
        }
        if (def.anonymousEnumDeclaration != null) {
            if (def.anonymousEnumDeclaration?.enumMembers != null) {
                for (enumMember in def.anonymousEnumDeclaration!!.enumMembers) {
                    if (!processor.execute(enumMember, state)) {
                        toContinue = false
                    }
                }
            }
            return toContinue
        }
        if (def.functionDeclaration != null) {
            return processor.execute(def.functionDeclaration!!, state)
        }
        if (def.importDeclaration != null) {
            for (import in def.importDeclaration!!.singleImports) {
                if (!processor.execute(import, state)) {
                    toContinue = false
                }
            }
            return toContinue
        }
        if (def.interfaceDeclaration != null) {
            if (def.interfaceDeclaration!!.interfaceOrClass?.structBody != null) {
                for (d in def.interfaceDeclaration!!.interfaceOrClass?.structBody!!.declarations) {
                    if (!processDeclaration(d, processor, state, lastParent, place)) {
                        toContinue = false
                    }
                }
            }
            if (!processor.execute(def.interfaceDeclaration!!.interfaceOrClass!!, state)) {
                toContinue = false
            }
            return toContinue
        }
        if (def.mixinDeclaration != null) {

        }
        if (def.mixinTemplateDeclaration != null) {
            for (declaration in def.mixinTemplateDeclaration!!.templateDeclaration!!.declarations) {
                if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
            if (!processor.execute(def.mixinTemplateDeclaration!!.templateDeclaration!!, state)) {
                return false
            }
            return toContinue
        }
        if (def.sharedStaticConstructor != null) {

        }
        if (def.sharedStaticDestructor != null) {

        }
        if (def.staticAssertDeclaration != null) {

        }
        if (def.staticConstructor != null) {

        }
        if (def.staticDestructor != null) {

        }
        if (def.structDeclaration != null) {
            if (def.structDeclaration!!.structBody?.declarations != null) {
                for (declaration in def.structDeclaration!!.structBody!!.declarations) {
                    if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                        toContinue = false
                    }
                }
            }
            if (!processor.execute(def.structDeclaration!!, state)) {
                return false
            }
            return toContinue
        }
        if (def.templateDeclaration != null) {
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
        if (def.unionDeclaration != null) {
            if (def.unionDeclaration!!.structBody?.declarations != null) {
                for (declaration in def.unionDeclaration!!.structBody!!.declarations) {
                    if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                        toContinue = false
                    }
                }
            }
            if (!processor.execute(def.unionDeclaration!!, state)) {
                return false
            }
            return toContinue
        }
        if (def.unittest != null) {

        }
        if (def.variableDeclaration?.autoDeclaration?.autoDeclarationParts != null) {
            for (initializer in def.variableDeclaration!!.autoDeclaration!!.autoDeclarationParts) {
                if (!processor.execute(initializer, state)) {
                    toContinue = false
                }
            }
            return toContinue
        }
        if (def.variableDeclaration?.declarators != null) {
            for (declarator in def.variableDeclaration!!.declarators) {
                if (!processor.execute(declarator, state)) {
                    toContinue = false
                }
            }
            return toContinue
        }
        if (def.eponymousTemplateDeclaration != null) {
            return processor.execute(def.eponymousTemplateDeclaration!!, state)
        }
        if (def.attributeDeclaration != null) {

        }
        if (def.invariant != null) {

        }
        if (def.versionSpecification != null) {

        }
        if (def.debugSpecification != null) {
        }
        for (decl in def.declarations) {
            if (!processDeclaration(decl, processor, state, lastParent, place)) {
                toContinue = false
            }
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
        for (dLangStatement in element.declarationOrStatements) {
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

    fun processDeclarationsOrStatements(declarationOrStatements: List<DeclarationOrStatement>, processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement?, place: PsiElement): Boolean {
        var toContinue = true
        for (declarationOrStatement in declarationOrStatements) {
            if (declarationOrStatement.declaration != null) {
                if (!ScopeProcessorImplUtil.processDeclaration(declarationOrStatement.declaration!!, processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
            if (declarationOrStatement.statement?.statementNoCaseNoDefault?.labeledStatement != null) {
                toContinue = declarationOrStatement.statement!!.statementNoCaseNoDefault!!.labeledStatement!!.processDeclarations(processor, state, lastParent, place)
            }
        }
        return toContinue
    }

}
