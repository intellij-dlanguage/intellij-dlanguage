package io.github.intellij.dlanguage.resolve

import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.search.GlobalSearchScope
import io.github.intellij.dlanguage.index.DModuleIndex
import io.github.intellij.dlanguage.psi.DLanguageFunctionLiteralExpression
import io.github.intellij.dlanguage.psi.DLanguageLambdaExpression
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.psi.scope.PsiScopesUtil
import io.github.intellij.dlanguage.resolve.ScopeProcessorImplUtil.processDeclaration
import io.github.intellij.dlanguage.resolve.ScopeProcessorImplUtil.processParameters
import io.github.intellij.dlanguage.resolve.ScopeProcessorImplUtil.processTemplateParameters
import io.github.intellij.dlanguage.utils.*


/**
 * implements processDeclarations for various statements
 * todo make cases named for when someone goto's case
 * todo make is expressions named
 */
object ScopeProcessorImpl {
    val logger = Logger.getInstance("io.github.intellij.dlanguage.resolve.ScopeProcessorImpl")

    /**
     * takes the elements declared in the given psi and passes them to the scope processor via the execute method. The scope processor will return false if it has found what it is "looking for". Note that certain declarations processors will not process child block statement/aggregate bodies since those have their own processor.
     * Some of the process declarations methods may return early while others will search through the entire scope
     * @param element
     * @param processor
     * @param state
     * @param lastParent todo make use of this to determine if scope statements/decldefs contained inside a element should be processed or not.
     * @param place
     * @return true if it should continue, false if it should stop
     */
    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: StructBody,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        return element.declarations.none { !processDeclaration(it, processor, state, lastParent, place) }
    }


    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: AliasInitializer,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (element.templateParameters != null) {
            if (!processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: AutoAssignment,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (element.templateParameters != null) {
            if (!processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: SpecifiedVariableDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (lastParent == element) return true
        for (declarator in element.identifierInitializers) {
            if (declarator == lastParent) return true
            if (!processor.execute(declarator, state)) {
                return false
            }
        }
        return true
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: IdentifierInitializer,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (element.templateParameters != null) {
            if (!processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }

        if (lastParent?.parent != element) return true
        if (lastParent == element.initializer)
            return true
        return processor.execute(element, state)
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: DLanguageFunctionLiteralExpression,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        var toContinue = true
        if (element.identifier != null) {
            if (!processor.execute(element.identifier!!, state)) {
                toContinue = false
            }
        }
        if (element.parameters != null) {
            if (!processParameters(element.parameters!!, processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        return toContinue
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: DLanguageLambdaExpression,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (element.parameters != null) {
            if (!processParameters(element.parameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: ClassDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        var toContinue = true
        if (element.templateParameters != null) {
            if (!processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        if (element.structBody?.declarations != null) {
            for (declaration in element.structBody?.declarations!!) {
                if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
        }
        return toContinue
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: InterfaceDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        var toContinue = true
        if (element.templateParameters != null) {
            if (!processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        if (element.structBody?.declarations != null) {
            for (declaration in element.structBody?.declarations!!) {
                if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
        }
        return toContinue
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: StructDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        var toContinue = true
        if (element.templateParameters != null) {
            if (!processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        if (element.structBody?.declarations != null) {
            for (declaration in element.structBody?.declarations!!) {
                if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
        }
        return toContinue
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: MixinTemplateDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
//        if (element.templateDeclaration?.templateParameters != null) {
//            if (!processTemplateParameters(element.templateDeclaration!!.templateParameters!!, processor, state, lastParent, place)) {
//                return false
//            }
//        }
        return true
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: TemplateMixinDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        var toContinue = true
        if (element.templateParameters != null) {
            if (!processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        for (declaration in element.declarations) {
            if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        return toContinue
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: TemplateDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        var toContinue = true
        if (element.templateParameters != null) {
            if (!processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        for (declaration in element.declarations) {
            if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        return toContinue
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: UnionDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        var toContinue = true
        if (element.templateParameters != null) {
            if (!processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        if (element.structBody?.declarations != null) {
            for (declaration in element.structBody?.declarations!!) {
                if (!processDeclaration(declaration, processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
        }
        return toContinue
    }

    fun processDeclarations(element: BlockStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        return PsiScopesUtil.walkChildrenScopes(element, processor, state, lastParent, place)
    }

    fun processDeclarations(element: DeclarationStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        element.declaration?:return true
        return ScopeProcessorImplUtil.processDeclaration(element.declaration!!, processor, state, lastParent, place)
    }

    fun processDeclarations(element: FunctionDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        //todo handle place
        if (element.parameters != null) {
            if (!processParameters(element.parameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        if (element.templateParameters != null) {
            if (!processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: StaticForeachDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
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

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: ForeachStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (lastParent == null || lastParent.parent != element) {
            // Parent element should not see our vars
            return true
        }

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

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: WhileStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        var toContinue = true
        if (element.ifCondition?.identifier != null) {
            if (!processor.execute(element.ifCondition!!, state)) {
                toContinue = false
            }
        }
        return toContinue
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: LabeledStatement, //todo this should probably not be scope processor based
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        /*if (element.declarationOrStatement?.declaration != null) {
            if (!ScopeProcessorImplUtil.processDeclaration(element.declarationOrStatement!!.declaration!!, processor, state, lastParent, place))
                return false
        }*/
        return processor.execute(element, state)
    }


    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: ForStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (lastParent == null || lastParent.parent != element) {
            // Parent element should not see our vars
            return true
        }

        if (element.statements.isEmpty())
            return true

        // TODO Extract this logic to be re-usable
        var found = false
        val declarationStatement = element.statements.first()
        if (declarationStatement !is DeclarationStatement)
            return true
        var start: PsiElement? = element.oP_PAR_LEFT
        while (start != null && start.node.elementType != DlangTypes.OP_SCOLON && start.node.elementType != DlangTypes.OP_BRACES_RIGHT) {
            if (declarationStatement == start) {
                found = true
                break
            }
            start = start.nextSibling
        }

        if (found && lastParent != declarationStatement) {
            if (!declarationStatement.processDeclarations(processor, state, lastParent, place))
                return false
        }
        return true
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: DoStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        return true//todo check that while statement's can/can't contain truthy/falsy variable declarations or casts
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: IfStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        //todo check for an else if
        var toContinue = true
        if (element.ifCondition?.identifier != null) {
            if (!processor.execute(element.ifCondition!!, state)) {
                toContinue = false
            }
        }
        /*if (!ScopeProcessorImplUtil.processDeclarationsOrStatements(element.declarationOrStatements, processor, state, lastParent, place)) {
            toContinue = false
        }*/
        return toContinue
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: SwitchStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
//        element.statement?.statementNoCaseNoDefault?.blockStatement?.declarationsAndStatements?.declarationOrStatements
        //todo truthy types in switch statement???//should declarations in the switch scope statement be processed or do they go out of scope
        return true
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: FinalSwitchStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        return true//see regular switch statement
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: WithStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        return true//todo I don't actually know how with statements work in D
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: SynchronizedStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        return true //how does scope work in synchronized D statements
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: EnumDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
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

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: Catch,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (element.identifier != null) {
            return processor.execute(element.identifier!!, state)
        }
        return true
    }

    fun processDeclarations(element: ImportDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (lastParent != null && lastParent.parent == element) {
            if (place !is ImportBind)
                return true
            // it is a specific symbol in the import bind, look for the original symbol
            val import = element.singleImports.last()
            for (file in DModuleIndex.getFilesByModuleName(element.project, import.importedModuleName, GlobalSearchScope.allScope(element.project)).toSet()) {
                if (file == element.containingFile) continue
                if (!file.processDeclarations(processor, state, lastParent, place))
                    return false
            }
            return true
        }
        // safeguard if call processDeclarations of others files
        // TODO should be place.containingFile != element.containingFile && !element.isPublicImport
        if (place.containingFile != element.containingFile) {
            return true
        }
        var singleImports = element.singleImports.toMutableList()
        // we have an import binding, search for it first
        if (element.importBindings != null) {
            // with import binding restricts the scope of the corresponding single import
            singleImports.removeLastOrNull()
            for (elt in element.importBindings!!.importBinds) {
                if (elt.namedImportBind != null) {
                    if (!processor.execute(elt.namedImportBind!!, state))
                        return false
                } else {
                    if (elt.identifier?.text == place.text) {
                        val base = element.singleImports.last()
                        if (base.importedModuleName.isBlank())
                            continue
                        for (file in DModuleIndex.getFilesByModuleName(element.project, base.importedModuleName, GlobalSearchScope.allScope(element.project)).toSet()) {
                            if (file == element.containingFile) continue
                            if (!file.processDeclarations(processor, state, lastParent, place))
                                return false
                        }
                    }
                }
            }
        }
        for (import in singleImports) {
            // import a = foobar;
            if (import.identifier != null) {
                if (!processor.execute(import, state)) {
                    return false
                }
                continue
            }

            // check the symbol itself
            if (import.importedModuleName.isBlank())
                continue
            for (file in DModuleIndex.getFilesByModuleName(element.project, import.importedModuleName, GlobalSearchScope.allScope(element.project)).toSet()) {
                if (file == element.containingFile) continue
                if (!file.processDeclarations(processor, state, lastParent, place))
                    return false
            }
        }
        return true
    }


    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: Constructor,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        element.parameters?.let {
            if (!processParameters(it, processor, state, lastParent, place)) {
                return false
            }
        }
        if (element.templateParameters != null) {
            if (!processTemplateParameters(element.templateParameters!!, processor, state, lastParent, place)) {
                return false
            }
        }
        return true

    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: ConditionalStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        var toContinue = true
        /*for (declarationOrStatement in element.declarationOrStatements) {
            if (declarationOrStatement.declaration != null) {
                if (!processDeclaration(declarationOrStatement.declaration!!, processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
        }*/
        return toContinue
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: ConditionalDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        var toContinue = true
        for (decl in element.declarations) {
            if (!processDeclaration(decl, processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        return toContinue
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: IsExpression,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {

        if (element.identifier != null) {
            return processor.execute(element.identifier!!, state)
        }
        return true
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: OutStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {

        if (element.identifier != null) {
            return processor.execute(element.identifier!!, state)
        }
        return true
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: DeclarationBlock,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        var toContinue = true
        for (decl in element.declarations) {
            if (!processDeclaration(decl, processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        return toContinue
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: AttributeSpecifier,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (element.declarationBlock == null)
            return true
        return element.declarationBlock!!.processDeclarations(processor, state, lastParent, place)
    }


/*
    @Suppress("UNUSED_PARAMETER")
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

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: ScopeStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement,
                            place: PsiElement): Boolean {
        return processDeclarations(element.nonEmptyStatement!!, processor, state, lastParent, place)
    }

    @Suppress("UNUSED_PARAMETER")
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
        }
        if (element.forStatement != null) {
            return true
        }
        if (element.foreachStatement != null) {
            return true
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
            return true
        }
        if (element.synchronizedStatement != null) {
            return true//they have there own scope
        }
        if (element.tryStatement != null) {
            return true
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
