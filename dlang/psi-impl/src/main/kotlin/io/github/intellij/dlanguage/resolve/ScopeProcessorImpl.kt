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
import io.github.intellij.dlanguage.psi.interfaces.DVisibility
import io.github.intellij.dlanguage.psi.scope.ElementDeclarationHint
import io.github.intellij.dlanguage.psi.scope.PsiScopesUtil
import io.github.intellij.dlanguage.resolve.ScopeProcessorImplUtil.processDeclaration
import io.github.intellij.dlanguage.resolve.ScopeProcessorImplUtil.processParameters
import io.github.intellij.dlanguage.utils.*


/**
 * implements processDeclarations for various statements
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
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
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
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
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
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
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
        if (lastParent == null || lastParent.parent != element) {
            return true
        }
        var toContinue = true
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
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
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
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
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
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
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
//            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
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
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
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
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
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
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
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
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
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
    fun processDeclarations(element: LabeledStatement,
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

    fun processDeclarations(element: SingleImport,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {

        if (element.importedModuleName.isBlank())
            return true

        // prevent infinite recursing check
        if (state.get<MutableList<String>>(PROCESSED_FILES_KEY)?.contains(element.importedModuleName) == true)
            return true

        for (file in DModuleIndex.getFilesByModuleName(element.project, element.importedModuleName, GlobalSearchScope.allScope(element.project)).toSet()) {
            if (file == element.containingFile) continue
            if (!file.processDeclarations(processor, state, lastParent, place))
                return false
        }
        return true
    }

    fun processDeclarations(element: ImportDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        val hint = processor.getHint(ElementDeclarationHint.KEY)
        if (hint != null && !hint.shouldProcess(ElementDeclarationHint.DeclarationKind.IMPORT)) {
            return true
        }

        if (lastParent != null && lastParent.parent == element) {
            if (place !is ImportBind)
                return true
            // it is a specific symbol in the import bind, look for the original symbol
            val import = element.singleImports.last()
            return import.processDeclarations(processor, state, lastParent, place)
        }
        // safeguard if call processDeclarations of others files
        if (place.containingFile != element.containingFile && element.visibility() != DVisibility.Public) {
            return true
        }
        var singleImports = element.singleImports.toMutableList()
        // we have an import binding, search for it first
        if (element.importBindings != null) {
            // with import binding restricts the scope of the corresponding single import
            val base = singleImports.removeLastOrNull() // remove the last as due to binding, we can only access to his bindings
            if (base == null)
                return true
            for (elt in element.importBindings!!.importBinds) {
                if (elt.namedImportBind != null) {
                    if (!processor.execute(elt.namedImportBind!!, state))
                        return false
                } else {
                    if (elt.identifier?.text == place.text) {
                        return base.processDeclarations(processor, state, lastParent, place) != false
                    }
                }
            }
            // import a = foobar;
            if (base.identifier != null) {
                if (!processor.execute(base, state)) {
                    return false
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
            if (!import.processDeclarations(processor, state, lastParent, place))
                return false
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
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
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

    fun processDeclarations(element: AttributeSpecifier,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (!processor.execute(element, state)) {
            return false
        }
        if (element.declarationBlock == null)
            return true
        return element.declarationBlock!!.processDeclarations(processor, state, lastParent, place)
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: TemplateParameters,
                                  processor: PsiScopeProcessor,
                                  state: ResolveState,
                                  lastParent: PsiElement?,
                                  place: PsiElement): Boolean {
        for (p in element.templateParameterList?.templateParameters ?: listOf()) {
            if (p is TemplateThisParameter) {
                if (p.templateTypeParameter == null)
                    continue
                if (!processor.execute(p.templateTypeParameter!!, state)) {
                    return false
                }
            } else {
                if (!processor.execute(p, state)) {
                    return false
                }
            }
        }
        return true
    }

}
