package net.masterthought.dlanguage.resolve

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import net.masterthought.dlanguage.psi.*
import net.masterthought.dlanguage.resolve.ScopeProcessorImplUtil.processDeclaration
import net.masterthought.dlanguage.utils.*


/**
 * implements processDeclarations for various statements
 * todo make cases named for when someone goto's case
 */
object ScopeProcessorImpl {
    /**
     * takes the elements declared in the given psi and passes them to the scope processor via the execute method. The scope processor will return false if it has found what it is "looking for". Note that certain declarations processors will not process child block statement/aggregate bodies since those have their own processor.
     * Some of the process declarations methods may return early while others will search throught the entire scope

     * @param element
     * *
     * @param processor
     * *
     * @param state
     * *
     * @param lastParent todo make use of this do determine if scope statements/decldefs contained inside a element should be processed or not.
     * *
     * @param place
     * *
     * @return
     */


    fun processDeclarations(element: StructBody,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        var toContinue = true
        for (declaration in element.declarations) {
            if (!ScopeProcessorImplUtil.processDeclaration(declaration, processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        return toContinue
    }


    fun processDeclarations(element: DLanguageAliasInitializer,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: DLanguageAutoDeclarationPart,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: DLanguageDeclarator,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: DLanguageFunctionLiteralExpression,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.parameters != null) {
            if (!ScopeProcessorImplUtil.processParameters(element.parameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: DLanguageLambdaExpression,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.parameters != null) {
            if (!ScopeProcessorImplUtil.processParameters(element.parameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: InterfaceOrClass,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: StructDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: MixinTemplateDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.templateDeclaration?.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateDeclaration!!.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: TemplateMixinDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.templateDeclaration?.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateDeclaration!!.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: EponymousTemplateDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: TemplateDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: UnionDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: FunctionDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //todo handle place
        if (element.parameters != null) {
            if (!ScopeProcessorImplUtil.processParameters(element.parameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        if (element.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: ForeachStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //todo handle place
        var shouldContinue = true
        if (element.foreachType != null) {
            if (!processor.execute(element.foreachType!!, state)) {
                shouldContinue = false
            }
        }
        if (element.foreachTypeList?.foreachTypes != null) {
            for (foreachType in element.foreachTypeList!!.foreachTypes) {
                if (!processor.execute(foreachType, state)) {
                    shouldContinue = false
                }
            }
        }
        return shouldContinue
    }

    fun processDeclarations(element: WhileStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //todo should handle place
        return true//todo check that while statement's can/can't contain truthy/falsy variable declarations or casts
    }

    fun processDeclarations(element: LabeledStatement, //todo
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        return processor.execute(element, state)
    }

    fun processDeclarations(element: ForStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //todo handle place
        val init = element.declarationOrStatements[0]
        var shouldContinue = true
        if (init.declaration?.variableDeclaration?.autoDeclaration?.autoDeclarationParts != null) {
            for (initializer in init.declaration!!.variableDeclaration!!.autoDeclaration!!.autoDeclarationParts) {
                if (!processor.execute(initializer, state)) {
                    shouldContinue = false
                }
            }
        }
        if (init.declaration?.variableDeclaration?.declarators != null) {
            for (declarator in init.declaration!!.variableDeclaration!!.declarators) {
                if (!processor.execute(declarator, state)) {
                    shouldContinue = false
                }
            }
        }
        //init.statement.statementNoCaseNoDefault//check that no var declarations could be in statement
        return shouldContinue
    }

    fun processDeclarations(element: DoStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        return true//todo check that while statement's can/can't contain truthy/falsy variable declarations or casts
    }

    fun processDeclarations(element: IfStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //todo check for an else if
        var toContinue = true
        if (element.ifCondition?.identifier != null) {
            if (!processor.execute(element.ifCondition!!, state)) {
                toContinue = false
            }
        }
        for (declarationOrStatement in element.declarationOrStatements) {
            if (declarationOrStatement.declaration != null) {
                if (!processDeclaration(declarationOrStatement.declaration!!, processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
        }
        return toContinue
    }

    fun processDeclarations(element: SwitchStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //todo truthy types in switch statement???//should declarations in the switch scope statementbe processed or do they go out of scope
        return true
    }

    fun processDeclarations(element: FinalSwitchStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        return true//see regular switch statement
    }

    fun processDeclarations(element: WithStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        return true//todo I don't actually know how with statements work in D
    }

    fun processDeclarations(element: SynchronizedStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        return true //how does scope work in synchronized D statements
    }

    fun processDeclarations(element: EnumDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        var toContinue = true
        if (element.enumBody?.enumMembers != null) {
            for (member in element.enumBody?.enumMembers!!) {
                if (!processor.execute(member, state)) {
                    toContinue = false
                }
            }
        }
        if (!processor.execute(element, state)) {
            toContinue = false
        }
        return toContinue
    }

    fun processDeclarations(element: Catch,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.identifier != null) {
            return processor.execute(element.identifier!!, state)
        }
        return true
    }


    /*
        //todo convert all calls of this method to direct
        fun processDeclarations(element: ParameterList,
                                processor: PsiScopeProcessor,
                                state: ResolveState,
                                lastParent: PsiElement,
                                place: PsiElement): Boolean {
            for (parameter in element.parameterList) {
                if (!processor.execute(parameter, state)) {
                    return false
                }
            }
            return true
        }
    */
    fun processDeclarations(element: Constructor,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {

        if (!ScopeProcessorImplUtil.processParameters(element.parameters!!, processor, state, lastParent, place)) {
            return false
        }
        if (element.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true

    }

    fun processDeclarations(element: ConditionalStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        var toContinue = true
        for (declarationOrStatement in element.declarationOrStatements) {
            if (declarationOrStatement.declaration != null) {
                if (!processDeclaration(declarationOrStatement.declaration!!, processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
        }
        return toContinue
    }

    fun processDeclarations(element: DeclarationsAndStatements,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        var toContinue = true
        for (declarationOrStatement in element.declarationOrStatements) {
            if (declarationOrStatement.declaration != null) {
                if (!processDeclaration(declarationOrStatement.declaration!!, processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
        }
        return toContinue
    }


/*
    fun processDeclarations(element: Statement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //should not descend into scope
        if (element.blockStatement != null) {
            return true
        }
        if (element.opScolon != null) {
            return true
        }
        return processDeclarations(element.nonEmptyStatement!!, processor, state, lastParent, place)
    }

    fun processDeclarations(element: ScopeStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        return processDeclarations(element.nonEmptyStatement!!, processor, state, lastParent, place)
    }

    fun processDeclarations(element: NonEmptyStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //should not descend into scope
        if (element.blockStatement != null) {
            return true
        }

        if (element.labeledStatement != null) {
            if (!(processor.execute(element.labeledStatement!!, state))) {
                return false
            }
            if (!element.labeledStatement!!.processDeclarations(processor, state, lastParent, place)) {
                return false
            }
            return true
        }
        if (element.declaration != null) {
            return ScopeProcessorImplUtil.processDeclarations(element.declaration!!, processor, state, lastParent, place)
        }
        if (element.declarationStatement != null) {
            return ScopeProcessorImplUtil.processDeclarations(element.declarationStatement!!.declaration, processor, state, lastParent, place)
        }
        if (element.ifStatement != null) {
            return true//no leakage of scope could occur
        }
        if (element.whileStatement != null) {
            return true//todo check if variable declarations in while statement conditions are a thing
        }
        if (element.doStatement != null) {
            return true//same as while statement
            //todo needs declaration process statement
        }
        if (element.forStatement != null) {
            return true
            //todo needs declaration process statement
        }
        if (element.foreachStatement != null) {
            return true
            //todo needs declaration process statement
        }
        if (element.switchStatement != null) {
            return true
        }
        if (element.finalSwitchStatement != null) {
            return true
        }
        if (element.continueStatement != null) {
            return true
        }
        if (element.breakStatement != null) {
            return true
        }
        if (element.returnStatement != null) {
            return true
        }
        if (element.gotoStatement != null) {
            return true
        }
        if (element.withStatement != null) {
            return true//idk how with statements work todo
        }
        if (element.synchronizedStatement != null) {
            return true//they have there own scope
        }
        if (element.tryStatement != null) {
            return true//todo declaration process needed for catches
        }
        if (element.scopeGuardStatement != null) {
            return true
        }
        if (element.throwStatement != null) {
            return true
        }
        if (element.asmStatement != null) {
            return true
        }
        if (element.pragmaStatement != null) {
            return true
        }
        if (element.mixinStatement != null) {
            //todo how is this going to be handled?
            return true
        }
        if (element.foreachRangeStatement != null) {
            return true
        }
        if (element.conditionalStatement != null) {
            //version, static if etc. Needs to process into these
            //descends into block statements that are hidden inside statement
            //todo when to use result and not
            var result: Boolean = true
            for (statement in element.conditionalStatement!!.statementList) {
                if (!(statement.processDeclarations(processor, state, lastParent, place))) {
                    result = false
                }
                if (statement.blockStatement?.statementList != null) {
                    if (!ScopeProcessorImpl.processDeclarations(statement.blockStatement!!.statementList!!, processor, state, lastParent, place)) {
                        result = false
                    }
                }
                if (statement.nonEmptyStatement?.blockStatement?.statementList != null) {
                    if (!ScopeProcessorImpl.processDeclarations(statement.nonEmptyStatement?.blockStatement!!.statementList!!, processor, state, lastParent, place)) {
                        result = false
                    }
                }
            }
            if (element.conditionalStatement!!.blockStatement?.statementList != null) {
                if (!element.conditionalStatement!!.blockStatement!!.statementList!!.processDeclarations(processor, state, lastParent, place)) {
                    result = false
                }
            }
            for (block in element.conditionalStatement!!.declarationBlockList) {
                if (!ScopeProcessorImplUtil.processDeclarationsWithinBlock(block, processor, state, lastParent, place)) {
                    result = false
                }
            }
            return result
        }
        if (element.expressionStatement != null) {
            return true
        }
        if (element.staticAssert != null) {
            return true
        }
        if (element.importDeclaration != null) {
            return true
        }
        if (element.templateMixin != null) {
            return true
        }
        if (element.expressionStatement != null) {
            return true
        }
        //no declarations can leak out of these
        if (element.caseRangeStatement != null) {
            return true
        }
        if (element.caseStatement != null) {
            return true
        }
        if (element.defaultStatement != null) {
            return true
        }

        throw IllegalStateException("this shouldn't happen")
    }
*/
}
