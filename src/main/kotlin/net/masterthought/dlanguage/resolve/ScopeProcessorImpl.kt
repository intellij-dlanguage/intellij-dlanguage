package net.masterthought.dlanguage.resolve

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
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
    fun processDeclarations(element: DeclDefs,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        var result: Boolean = true
        //don't replace this loop with kotlin's any. This applies to any loop on this file.
        for (def in element.declDefList) {
            if (!(def.processDeclarations(processor, state, lastParent, place))) {
                result = false
            }
        }
        return result
    }

    fun processDeclarations(element: StatementList,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        var result: Boolean = true
        //todo handle place and iterate backwards from place
        for (statement in element.statementList) {
            if (!(statement.processDeclarations(processor, state, lastParent, place))) {
                result = false//todo use statement processDeclartions
            }
        }
        return result
    }

    fun processDeclarations(element: FunctionLiteral,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        var result: Boolean = true//idk if this should be a thing
        if (element.parameterAttributes?.parameters != null) {
            if (!ScopeProcessorImplUtil.processParameters(element.parameterAttributes!!.parameters, processor, state, lastParent, place)) {
                result = false
            }
        }
        if (element.parameterMemberAttributes?.parameters != null) {
            if (!ScopeProcessorImplUtil.processParameters(element.parameterMemberAttributes!!.parameters, processor, state, lastParent, place)) {
                result = false
            }
        }
        if (element.lambda != null) {
            if (element.lambda!!.parameterAttributes?.parameters != null) {
                if (!ScopeProcessorImplUtil.processParameters(element.lambda!!.parameterAttributes!!.parameters, processor, state, lastParent, place)) {
                    result = false
                }
            }
            if (element.lambda!!.parameterMemberAttributes?.parameters != null) {
                if (!ScopeProcessorImplUtil.processParameters(element.lambda!!.parameterMemberAttributes!!.parameters, processor, state, lastParent, place)) {
                    result = false
                }
            }
        }
        return result
    }

    fun processDeclarations(element: Lambda,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (element.parameterAttributes?.parameters != null) {
            if (!ScopeProcessorImplUtil.processParameters(element.parameterAttributes!!.parameters, processor, state, lastParent, place)) {
                return false
            }
        }
        if (element.parameterMemberAttributes?.parameters != null) {
            if (!ScopeProcessorImplUtil.processParameters(element.parameterMemberAttributes!!.parameters, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: Parameters,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        var result: Boolean = true
        if (element.parameterList?.parameterList != null) {
            for (parameter in element.parameterList?.parameterList!!) {
                if (!processor.execute(parameter, state)) {
                    result = false
                }
            }
        }
        return result
    }

    fun processDeclarations(element: VarFuncDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (!ScopeProcessorImplUtil.processParameters(element.parameters, processor, state, lastParent, place)) {
            return false
        }
        if (element.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: ClassDeclaration,
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

    fun processDeclarations(element: TemplateMixinDeclaration,
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

    fun processDeclarations(element: FuncDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //todo handle place
        if (!ScopeProcessorImplUtil.processParameters(element.parameters, processor, state, lastParent, place)) {
            return false
        }
        if (element.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: AliasDeclarationY,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //todo handle place
        if (element.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: AutoDeclarationY,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //todo handle place
        if (element.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: AliasDeclarationSingle,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
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

    fun processDeclarations(element: DeclaratorInitializer,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //todo handle place
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
        for (foreachType in element.foreachTypeList.foreachTypeList) {
            if (!processor.execute(foreachType, state)) {
                shouldContinue = false
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

    fun processDeclarations(element: LabeledStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //todo should handle place
        if (element.statement != null) {
            return processDeclarations(element.statement!!, processor, state, lastParent, place)
        }
        return true
    }

    fun processDeclarations(element: ForStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //todo handle place
        if (element.initialize == null || element.initialize!!.statement == null)
            return true
        return element.initialize!!.statement!!.processDeclarations(processor, state, lastParent, place)
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
        if (element.ifCondition?.conditionVariableDeclaration != null) {
            return processor.execute(element.ifCondition!!.conditionVariableDeclaration!!, state)
        }
        return true
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

    fun processDeclarations(element: EnumMembers,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        for (member in element.enumMemberList) {
            if (!processor.execute(member, state)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: Catch,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        return processor.execute(element.catchParameter, state)
    }

    fun processDeclarations(element: ForeachRangeStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //see foreach
        return processor.execute(element.foreachType, state)
    }

    fun processDeclarations(def: DeclDef,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        if (def.attributeSpecifier != null) {
            if (def.attributeSpecifier!!.declarationBlock != null) {
                return ScopeProcessorImplUtil.processDeclarationsWithinBlock(def.attributeSpecifier!!.declarationBlock!!, processor, state, lastParent, place)
            }
            return true
        }
        if (def.declaration != null) {
            return ScopeProcessorImplUtil.processDeclarations(def.declaration!!, processor, state, lastParent, place)
        }
        if (def.constructor != null) {
            //todo make sure names for constructors are handled correctly
            return processor.execute(def.constructor!!, state)
        }
        if (def.aliasThis != null) {
            //todo handle alias this
            return true
        }
        if (def.conditionalDeclaration != null) {
            return processDeclarations(def.conditionalDeclaration!!, processor, state, lastParent, place)
        }
        if (def.templateDeclaration != null) {
            if (!processor.execute(def.templateDeclaration!!, state)) {
                return false
            }
            if (def.templateDeclaration!!.declDefs != null) {
                if (!def.templateDeclaration!!.declDefs!!.processDeclarations(processor, state, lastParent, place)) {
                    return false
                }
            }
            return true
        }
        if (def.templateMixinDeclaration != null) {
            if (!processor.execute(def.templateMixinDeclaration!!, state)) {
                return false
            }
            if (def.templateMixinDeclaration!!.declDefs != null) {
                if (!def.templateMixinDeclaration!!.declDefs!!.processDeclarations(processor, state, lastParent, place)) {
                    return false
                }
            }
            return true
        }
        if (def.templateMixin != null) {
            //todo handle mixins
            return true
        }
        if (def.mixinDeclaration != null) {
            //todo handle mixins
            return true
        }
        if (def.staticIfCondition != null) {
            return def.staticIfCondition!!.nextSibling?.processDeclarations(processor, state, lastParent, place) ?: true//process the declaration block after static if
        }
        if (def.staticElseCondition != null) {
            if (def.staticElseCondition!!.declarationBlock != null) {
                return ScopeProcessorImplUtil.processDeclarationsWithinBlock(def.staticElseCondition!!.declarationBlock!!, processor, state, lastParent, place)
            } else {
                return true
            }
        }
        if (def.opScolon != null) {
            return true
        }
        if (def.postblit != null || def.destructor != null || def.allocator != null || def.deallocator != null || def.invariant != null || def.unitTesting != null || def.staticConstructor != null || def.staticDestructor != null || def.sharedStaticConstructor != null || def.sharedStaticDestructor != null || def.staticAssert != null || def.debugSpecification != null || def.versionSpecification != null) {
            return true
        }
        throw IllegalStateException("this should never happen")
    }

    fun processDeclarations(element: ConditionalStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //always recurse in since this is a compile time condition
        //handle place and lastParent
        for (dLangStatement in element.statementList) {
            if (!dLangStatement.processDeclarations(processor, state, lastParent, place)) {
                return false
            }
        }
        for (block in element.declarationBlockList) {
            if (!ScopeProcessorImplUtil.processDeclarationsWithinBlock(block, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    fun processDeclarations(element: ConditionalDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        //todo the available getters seem to not match the grammar for this
        var defNotFound = true
        if (element.declDefs != null) {
            for (def in element.declDefs!!.declDefList) {
                if (!def.processDeclarations(processor, state, lastParent, place)) {
                    defNotFound = false
                }
            }
        }
        for (child in element.children) {
            if(child is DeclarationBlock){
                if (!ScopeProcessorImplUtil.processDeclarationsWithinBlock(child, processor, state, lastParent, place)) {
                    defNotFound = false
                }
            }
        }

        return defNotFound
    }

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

    fun processDeclarations(element: Constructor,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {

        if (!ScopeProcessorImplUtil.processParameters(element.parameters, processor, state, lastParent, place)) {
            return false
        }
        if (element.templateParameters != null) {
            if (!ScopeProcessorImplUtil.processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true

    }

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

}
