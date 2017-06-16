package net.masterthought.dlanguage.resolve

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.util.PsiTreeUtil
import net.masterthought.dlanguage.psi.interfaces.HasParameters
import net.masterthought.dlanguage.psi.interfaces.HasTemplateParameters
import net.masterthought.dlanguage.psi.interfaces.VariableDeclaration
import net.masterthought.dlanguage.utils.*

/**
 * Created by francis on 6/15/2017.
 */

object ScopeProcessorImplUtil {

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
                if (processor.execute(element.aliasDeclaration!!.aliasDeclarationSingle!!, state)) {
                    return false
                }
            }

        }
        if (element.aggregateDeclaration != null) {
            if (!element.aggregateDeclaration!!.processDeclarations(processor, state, lastParent, place)) {
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

    fun processParameters(element: Parameters,
                          processor: PsiScopeProcessor,
                          state: ResolveState,
                          lastParent: PsiElement,
                          place: PsiElement): Boolean {
        for (p in element.parameterList?.parameterList ?: listOf()) {
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
        for (p in element.templateParameterList?.templateParameterList ?: listOf()) {
            if (!processor.execute(p, state)) {
                return false
            }
        }
        return true
    }


}
