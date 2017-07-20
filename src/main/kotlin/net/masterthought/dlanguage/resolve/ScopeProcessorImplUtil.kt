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
                           lastParent: PsiElement,
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
                if (!declaration.processDeclarations(processor, state, lastParent, place)) {
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
        if (def.functionDeclaration != null) {
            return processor.execute(def.functionDeclaration!!, state)
        }
        if (def.importDeclaration != null) {
            for (singleImport in def.importDeclaration!!.singleImports) {
                if (!processor.execute(singleImport, state)) {
                    toContinue = false
                }
            }
            return toContinue
        }
        if (def.interfaceDeclaration != null) {
            if (def.interfaceDeclaration!!.interfaceOrClass?.structBody != null) {
                for (d in def.interfaceDeclaration!!.interfaceOrClass?.structBody!!.declarations) {
                    if (!d.processDeclarations(processor, state, lastParent, place)) {
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
                if (!declaration.processDeclarations(processor, state, lastParent, place)) {
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
                    if (!declaration.processDeclarations(processor, state, lastParent, place)) {
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
            if (def.templateDeclaration!!.declarations != null) {
                for (declaration in def.templateDeclaration!!.declarations) {
                    if (!declaration.processDeclarations(processor, state, lastParent, place)) {
                        toContinue = false
                    }
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
                    if (!declaration.processDeclarations(processor, state, lastParent, place)) {
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
        if (def.attributeDeclaration != null) {

        }
        if (def.invariant != null) {

        }
        if (def.versionSpecification != null) {

        }
        if (def.debugSpecification != null) {
        }
        for (decl in def.declarations) {
            if (!decl.processDeclarations(processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        return toContinue
        throw IllegalStateException("this should never happen")
    }

    fun processConditionalDeclaration(element: ConditionalDeclaration,
                                      processor: PsiScopeProcessor,
                                      state: ResolveState,
                                      lastParent: PsiElement,
                                      place: PsiElement): Boolean {

        var defNotFound = true
        for (declaration in element.declarations) {
            if (!ScopeProcessorImplUtil.processDeclaration(declaration, processor, state, lastParent, place)) {
                defNotFound = false
            }
        }
        return defNotFound
    }


    fun processConditionalStatement(element: ConditionalStatement,
                                    processor: PsiScopeProcessor,
                                    state: ResolveState,
                                    lastParent: PsiElement,
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

    /*

    /**
     * effectively a utility method for decldef and statement
     */
    fun processDeclarations(element: Declaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.enumDeclaration != null) {
            if (!processor.execute(element.enumDeclaration!!, state)) {
                return false
            }
            if (element.enumDeclaration!!.enumBody?.enumMembers?.enumMemberList != null) {
                for (member in element.enumDeclaration!!.enumBody!!.enumMembers.enumMemberList) {
                    if (!processor.execute(member, state)) {
                        return false
                    }
                }
            }
            if (element.enumDeclaration?.anonymousEnumDeclaration?.enumMembers != null) {
                for (member in element.enumDeclaration!!.anonymousEnumDeclaration!!.enumMembers!!.enumMemberList) {
                    if (!processor.execute(member, state)) {
                        return false
                    }
                }
            }
        }
        if (element.funcDeclaration != null) {
            if (!processor.execute(element.funcDeclaration!!, state)) {
                return false
            }
        }
        if (element.varDeclarations != null) {
            val `var`: VariableDeclaration
            if (element.varDeclarations!!.autoDeclaration != null) {
                `var` = element.varDeclarations!!.autoDeclaration!!.autoDeclarationX.autoDeclarationY
            } else {
                `var` = element.varDeclarations!!.declarators!!.declaratorInitializer
            }
            if (!processor.execute(`var`, state)) {
                return false
            }
        }
        if (element.aliasDeclaration != null) {
            //todo make the alias declaration rule non recursive
            fun getAliasDeclarations(x: AliasDeclarationX): MutableList<AliasDeclarationY> {
                val list: MutableList<AliasDeclarationY> = mutableListOf(x.aliasDeclarationY)
                if (x.aliasDeclarationX != null) {
                    list.addAll(getAliasDeclarations(x.aliasDeclarationX!!))
                }
                return list
            }
            if (element.aliasDeclaration!!.aliasDeclarationX != null) {
                for (declaration in getAliasDeclarations(element.aliasDeclaration!!.aliasDeclarationX!!)) {
                    if (!processor.execute(declaration, state)) {
                        return false
                    }
                }
            }
            if (element.aliasDeclaration!!.aliasDeclarationSingle != null) {
                if (!processor.execute(element.aliasDeclaration!!.aliasDeclarationSingle!!, state)) {
                    return false
                }
            }

        }
        if (element.aggregateDeclaration != null) {
            if (!ScopeProcessorImplUtil.processDeclarations(element.aggregateDeclaration!!, processor, state, lastParent, place)) {
                return false
            }
        }
        if (element.importDeclaration != null) {
            for (import in PsiTreeUtil.findChildrenOfType(element.importDeclaration, Import::class.java)) {
                if (!processor.execute(import, state)) {
                    return false
                }
            }
        }
        if (element.templateDeclaration != null) {
            if (!processor.execute(element.templateDeclaration!!, state)) {
                return false
            }
            if (element.templateDeclaration!!.declDefs != null) {
                if (!element.templateDeclaration!!.declDefs!!.processDeclarations(processor, state, lastParent, place)) {
                    return false
                }
            }
        }
        if (element.varFuncDeclaration != null) {
            return processor.execute(element.varFuncDeclaration!!, state)
        }
        return true
    }

    /**
     * effectively a utility method for Declaration
     */
    fun processDeclarations(element: AggregateDeclaration, processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.classDeclaration != null) {
            if (!processor.execute(element.classDeclaration!!, state)) {
                return false
            }
            if (element.classDeclaration!!.aggregateBody?.declDefs != null) {
                if (!element.classDeclaration!!.aggregateBody!!.declDefs!!.processDeclarations(processor, state, lastParent, place)) {
                    return false
                }
            }
        }
        if (element.interfaceDeclaration != null) {
            if (!processor.execute(element.interfaceDeclaration!!, state)) {
                return false
            }
            if (element.interfaceDeclaration!!.aggregateBody?.declDefs != null) {
                if (!element.interfaceDeclaration!!.aggregateBody!!.declDefs!!.processDeclarations(processor, state, lastParent, place)) {
                    return false
                }
            }
        }
        if (element.unionDeclaration != null) {
            if (!processor.execute(element.unionDeclaration!!, state)) {
                return false
            }
            if (element.unionDeclaration!!.aggregateBody?.declDefs != null) {
                if (!element.unionDeclaration!!.aggregateBody!!.declDefs!!.processDeclarations(processor, state, lastParent, place)) {
                    return false
                }
            }
        }
        if (element.structDeclaration != null) {
            if (!processor.execute(element.structDeclaration!!, state)) {
                return false
            }
            if (element.structDeclaration!!.aggregateBody?.declDefs != null) {
                if (!element.structDeclaration!!.aggregateBody!!.declDefs!!.processDeclarations(processor, state, lastParent, place)) {
                    return false
                }
            }
        }
        return true
    }

    fun processDeclarationsWithinBlock(element: DeclarationBlock, processor: PsiScopeProcessor,
                                       state: ResolveState,
                                       lastParent: PsiElement,
                                       place: PsiElement): Boolean {
        if (element.declDef != null) {
            if (!element.declDef!!.processDeclarations(processor, state, lastParent, place)) {
                return false
            }
        }
        if (element.declDefs != null) {
            for (def in element.declDefs!!.declDefList) {
                if (!def.processDeclarations(processor, state, lastParent, place)) {
                    return false
                }
            }
        }
        return true
    }

    fun processTemplateParameters(element: HasTemplateParameters,
                                  processor: PsiScopeProcessor,
                                  state: ResolveState,
                                  lastParent: PsiElement,
                                  place: PsiElement): Boolean {
        for (templateParameter in element.templateParametersList) {
            if (!processor.execute(templateParameter, state)) {
                return false
            }
        }
        return true
    }

    fun processParameters(element: HasParameters,
                          processor: PsiScopeProcessor,
                          state: ResolveState,
                          lastParent: PsiElement,
                          place: PsiElement): Boolean {
        for (p in element.parameterList) {
            if (!processor.execute(p, state)) {
                return false
            }
        }
        return true
    }


    */
    fun processParameters(element: Parameters,
                          processor: PsiScopeProcessor,
                          state: ResolveState,
                          lastParent: PsiElement,
                          place: PsiElement): Boolean {
        for (p in element.parameters) {
            if (!processor.execute(p, state)) {
                return false
            }
        }
        return true
    }

    fun processTemplateParameters(element: TemplateParameters,
                                  processor: PsiScopeProcessor,
                                  state: ResolveState,
                                  lastParent: PsiElement,
                                  place: PsiElement): Boolean {
        for (p in element.templateParameterList?.templateParameters ?: listOf()) {
            if (!processor.execute(p, state)) {
                return false
            }
        }
        return true
    }

}
