package io.github.intellij.dlanguage.resolve

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.search.GlobalSearchScope
import io.github.intellij.dlanguage.index.DModuleIndex
import io.github.intellij.dlanguage.psi.DLanguageBaseClassList
import io.github.intellij.dlanguage.psi.DLanguageFunctionLiteralExpression
import io.github.intellij.dlanguage.psi.DLanguageLambdaExpression
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.psi.interfaces.DVisibility
import io.github.intellij.dlanguage.psi.scope.ElementDeclarationHint
import io.github.intellij.dlanguage.psi.scope.PsiScopesUtil
import io.github.intellij.dlanguage.psi.types.DUnknownType
import io.github.intellij.dlanguage.psi.types.UserDefinedDType
import io.github.intellij.dlanguage.utils.*


/**
 * implements processDeclarations for various statements
 */
object ScopeProcessorImpl {
    fun processDeclarations(element: StructBody,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        var toContinue = true
        for (declaration in element.declarations) {
            // don’t re-process the declaration we just processed
            if (lastParent === declaration)
                continue
            if (!declaration.processDeclarations(processor, state, lastParent, place))
                toContinue = false
        }
        return toContinue
    }

    fun processDeclarations(element: AliasDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        // the element is in the alias declaration, so it can’t match to this declaration
        if (lastParent != null && lastParent.parent === element)
            return true

        if (element.aliasInitializers.isNotEmpty()) {
            for (varDeclaration in element.aliasInitializers) {
                if(!varDeclaration.processDeclarations(processor, state, lastParent, place)) {
                    return false
                }
            }
        }

        if (element.declaratorIdentifiers.isNotEmpty()) {
            for (varDeclaration in element.declaratorIdentifiers) {
                if (!processor.execute(varDeclaration, state)) {
                    return false
                }
            }
        }
        return true
    }

    fun processDeclarations(element: AliasInitializer,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {

        // our value can see our parameters
        if (element.templateParameters != null) {
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
                return false
            }
        }

        // Element in initializer cannot see our elements
        if (lastParent != null && lastParent.parent === element) {
            return true
        }

        if (!processor.execute(element, state))
            return false

        return true
    }

    fun processDeclarations(element: AutoAssignment,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        // Our value can see our parameters
        if (element.templateParameters != null) {
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
                return false
            }
        }

        // Our children cannot see our elements
        if (lastParent != null && lastParent.parent === element)
            return true

        return processor.execute(element, state)
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: AutoDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        // Our children can only access to elements declared before them
        if (lastParent != null && lastParent.parent === element) {
            return PsiScopesUtil.walkChildrenScopes(element, processor, state, lastParent, place)
        }

        for (declarator in element.autoAssignments) {
            if (!declarator.processDeclarations(processor, state, lastParent, place)) {
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
        // Our children can only access to elements declared before them
        if (lastParent != null && lastParent.parent === element) {
            return PsiScopesUtil.walkChildrenScopes(element, processor, state, lastParent, place)
        }

        for (declarator in element.identifierInitializers) {
            if (declarator == lastParent) return true
            if (!processor.execute(declarator, state)) {
                return false
            }
        }
        return true
    }

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

        // Our children cannot see our elements
        if (lastParent != null && lastParent.parent === element)
            return true

        return processor.execute(element, state)
    }

    fun processDeclarations(element: DLanguageFunctionLiteralExpression,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (lastParent != null && lastParent.parent !== element) {
            // Outside elements cannot see our inner elements
            return true
        }
        var toContinue = true
        if (element.parameters != null) {
            for (parameter in element.parameters!!.parameters) {
                if (!parameter.processDeclarations(processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
        }
        return toContinue
    }

    fun processDeclarations(element: DLanguageLambdaExpression,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (lastParent != null && lastParent.parent !== element) {
            // Outside elements cannot see our inner elements
            return true
        }
        if (element.parameters != null) {
            for (parameter in element.parameters!!.parameters) {
                if (!parameter.processDeclarations(processor, state, lastParent, place)) {
                    return false
                }
            }
        }
        return true
    }

    fun processDeclarations(element: ClassDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {

        val hint = processor.getHint(ElementDeclarationHint.KEY)
        if (hint != null && !hint.shouldProcess(ElementDeclarationHint.DeclarationKind.CLASS)) {
            return true
        }

        // Base class list cannot match itself (prevent infinite recursion)
        if (lastParent != null && lastParent == element.baseClassList) {
            return true
        }

        if (!processor.execute(element, state))
            return false

        // We are outside elements so check our members
        if (lastParent != null && lastParent.parent !== element)
            return true

        if (lastParent == null && element.structBody != null) {
            if(!element.structBody!!.processDeclarations(processor, state, lastParent, place))
                return false
        }

        // We are inside elements so check our parameters, no need to check body, it was already processed
        var toContinue = true
        if (element.templateParameters != null) {
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
                toContinue = false
            }
        }

        // To prevent recursivity, if we checked the parent element, we are sure we don’t want to check the element itself (a child)
        if (lastParent is DLanguageBaseClassList) return true

        toContinue = toContinue && (element.parentClass?.processDeclarations(processor, state, null, place)?:true)

        if (hint != null && hint.shouldProcess(ElementDeclarationHint.DeclarationKind.INTERFACE)) {
            val processedInterfaces = mutableSetOf<InterfaceDeclaration>()
            for (parentInterface in element.interfaces) {
                if (!processDeclarationInInterface(parentInterface, processor, state, processedInterfaces, null, place)) {
                    toContinue = false
                }
            }
        }
        return toContinue
    }

    fun processDeclarations(element: InterfaceDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        return processDeclarationInInterface(element, processor, state, mutableSetOf(), lastParent, place);
    }

    private fun processDeclarationInInterface(element: InterfaceDeclaration,
                                      processor: PsiScopeProcessor,
                                      state: ResolveState,
                                      visited: MutableSet<InterfaceDeclaration>,
                                      lastParent: PsiElement?,
                                      place: PsiElement): Boolean {
        if (!visited.add(element))
            return true

        val hint = processor.getHint(ElementDeclarationHint.KEY)
        if (hint != null && !hint.shouldProcess(ElementDeclarationHint.DeclarationKind.INTERFACE)) {
            return true
        }

        // Base class list cannot match itself (prevent infinite recursion)
        if (lastParent != null && lastParent == element.baseClassList) {
            return true
        }

        if (!processor.execute(element, state))
            return false

        // We are outside elements so check our members
        if (lastParent != null && lastParent.parent !== element)
            return true

        if (lastParent == null && element.structBody != null) {
            if(!element.structBody!!.processDeclarations(processor, state, lastParent, place))
                return false
        }

        // We are inside elements so check our parameters, no need to check body, it was already processed
        var toContinue = true
        if (element.templateParameters != null) {
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
                toContinue = false
            }
        }

        if (!toContinue) return false

        // To prevent recursivity, if we checked the parent element, we are sure we don’t want to check the element itself (a child)
        if (lastParent is DLanguageBaseClassList) return true

        for (parentInterface in element.interfaces) {
            if (!processDeclarationInInterface(parentInterface, processor, state, visited, null, place)) {
                toContinue = false
            }
        }

        return toContinue
    }

    fun processDeclarations(element: StructDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (element.name != null) {
            if (!processor.execute(element, state))
                return false
        }

        // We are outside elements so check our members
        if (lastParent != null && lastParent.parent !== element)
            return true

        if (lastParent == null && element.structBody != null) {
            if(!element.structBody!!.processDeclarations(processor, state, lastParent, place))
                return false
        }

        // We are inside elements so check our parameters, no need to check body, it was already processed
        var toContinue = true
        if (element.templateParameters != null) {
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        return toContinue
    }

    fun processDeclarations(element: TemplateMixinDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (!processor.execute(element, state))
            return false

        var toContinue = true
        // Outside elements cannot see our parameter nor our members
        if (lastParent != null && lastParent.parent !== element) {
            return true
        }

        if (element.templateParameters != null) {
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
                toContinue = false
            }
        }

        // Only check declarations if we want to see inside
        if (lastParent === null) {
            for (declaration in element.declarations) {
                if (!declaration.processDeclarations(processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
        }

        return toContinue
    }

    fun processDeclarations(element: TemplateDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (lastParent == null || lastParent.parent == element) {
            // Only our member can see our elements

            if (!PsiScopesUtil.walkChildrenScopes(element, processor, state, lastParent, place))
                return false

            if (element.templateParameters != null) {
                if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
                    return false
                }
            }
        }

        if(!processor.execute(element, state))
            return false

        return true
    }

    fun processDeclarations(element: UnionDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (element.name != null) {
            if (!processor.execute(element, state))
                return false
        }

        // We are outside elements so check our members
        if (lastParent != null && lastParent.parent !== element)
            return true

        if (lastParent == null && element.structBody != null) {
            if(!element.structBody!!.processDeclarations(processor, state, lastParent, place))
                return false
        }

        var toContinue = true
        if (element.templateParameters != null) {
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
                toContinue = false
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
        // don’t process twice, we just processed this element
        if (lastParent != null && lastParent.parent === element)
            return true
        return element.declaration?.processDeclarations(processor, state, lastParent, place) ?: true
    }

    fun processDeclarations(element: FunctionDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (lastParent !== null && lastParent.parent == element) {
            // Only our member can see our elements

            if (!PsiScopesUtil.walkChildrenScopes(element, processor, state, lastParent, place))
                return false

            if (element.parameters != null) {
                for (parameter in element.parameters!!.parameters) {
                    if (!parameter.processDeclarations(processor, state, lastParent, place)) {
                        return false
                    }
                }
            }
            if (element.templateParameters != null) {
                if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
                    return false
                }
            }
        }

        if(!processor.execute(element, state))
            return false

        return true
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: StaticForeachDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (lastParent != null && (lastParent.parent !== element || lastParent in element.expressions)) {
            // declaration and our expressions neither cannot see our vars
            return true
        }

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

        for (declaration in element.declarations) {
            // Don’t processe the declaration twice, we just processed it
            if (lastParent != null && lastParent === element) {
                continue
            }
            if (!declaration.processDeclarations(processor, state, lastParent, place))
                shouldContinue = false
        }

        return shouldContinue
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: ForeachStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (lastParent == null || lastParent.parent !== element || lastParent in element.expressions) {
            // Parent element should not see our vars, and the iterated variable cannot see our vars
            return true
        }

        var shouldContinue = true
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
        return processor.execute(element, state)
    }


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
        if (lastParent == null || lastParent.parent != element || lastParent === element.ifCondition) {
            // Parent element should not see our vars and value of the declared variable should not see the variable itself
            return true
        }
        var toContinue = true
        if (element.ifCondition?.identifier != null) {
            if (!processor.execute(element.ifCondition!!, state)) {
                toContinue = false
            }
        }
        return toContinue
    }

    fun processDeclarations(element: CaseStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (lastParent == null || lastParent.parent != element) {
            // Parent element should not see our vars
            return true
        }

        return PsiScopesUtil.walkChildrenScopes(element, processor, state, lastParent, place)
    }

    fun processDeclarations(element: CaseRangeStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (lastParent == null || lastParent.parent != element) {
            // Parent element should not see our vars
            return true
        }
        return PsiScopesUtil.walkChildrenScopes(element, processor, state, lastParent, place)
    }

    fun processDeclarations(element: DefaultStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (lastParent == null || lastParent.parent != element) {
            // Parent element should not see our vars
            return true
        }

        return PsiScopesUtil.walkChildrenScopes(element, processor, state, lastParent, place)
    }

    fun processDeclarations(element: WithStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {

        // Outside elements cannot see our members
        if (lastParent != null && lastParent.parent !== element)
            return true
        // Our expression cannot see our members
        if (lastParent != null && lastParent === element.expression)
            return true

        val hint = processor.getHint(ElementDeclarationHint.KEY)

        // Eager return if the processor does not accept elements
        // FIXME: introduce proper type, this check is mainly to prevent PackageOrModule to check it (optimization)
        if (hint != null && !hint.shouldProcess(ElementDeclarationHint.DeclarationKind.CLASS))
            return true

        val elementType = element.expression?.dType
        if (elementType != null && elementType !is DUnknownType) {
            if (elementType is UserDefinedDType) {
                val resolvedTypeElement = elementType.resolve()
                if (!resolvedTypeElement.processDeclarations(processor, state, null, place))
                    return false
            }
        }
        return true
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: EnumDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        if (!processor.execute(element, state)) {
            return false
        }

        // Don’t look into the enum if we are known to be outside
        if (lastParent != null && lastParent.parent !== element)
            return true

        var toContinue = true
        if (element.enumBody?.enumMembers != null) {
            for (member in element.enumBody?.enumMembers!!) {
                if (!processor.execute(member, state)) {
                    toContinue = false
                }
            }
        }
        return toContinue
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: AnonymousEnumDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        for (enumMember in element.enumMembers) {
            // enum member cannot be seen from his assigned value (and values after the declaration neither)
            if (lastParent != null && lastParent === enumMember)
                continue

            if (!processor.execute(enumMember, state)) {
                return false
            }
        }
        return true
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: Catch,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        // Outside elements cannot see our variable
        if (lastParent != null && lastParent.parent !== element)
            return true

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
        if (state.get(PROCESSED_FILES_KEY)?.contains(element.importedModuleName) == true)
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
        if (!processor.execute(element, state))
            return false
        val hint = processor.getHint(ElementDeclarationHint.KEY)
        if (hint != null && !hint.shouldProcess(ElementDeclarationHint.DeclarationKind.IMPORT)) {
            return true
        }

        val canProcessStatic = state.get(IS_QUALIFIED_SYMBOL)?:false
        if (element.kW_STATIC != null && !canProcessStatic) {
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
        val singleImports = element.singleImports.toMutableList()
        // we have an import binding, search for it first
        if (element.importBindings != null) {
            // with import binding restricts the scope of the corresponding single import
            val base = singleImports.removeLastOrNull()
                ?: return true // remove the last as due to binding, we can only access to his bindings
            for (elt in element.importBindings!!.importBinds) {
                if (elt.namedImportBind != null) {
                    if (!processor.execute(elt.namedImportBind!!, state))
                        return false
                } else {
                    val found = elt.reference?.resolve()
                    if (found != null) {
                        processor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, found.containingFile)
                        if (!processor.execute(found, state))
                            return false
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

    fun processDeclarations(element: Constructor,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {

        // External elements cannot see our parameters
        if (lastParent != null && lastParent.parent !== element)
            return true

        element.parameters?.let {
            for (parameter in element.parameters!!.parameters) {
                if (!parameter.processDeclarations(processor, state, lastParent, place)) {
                    return false
                }
            }
        }
        if (element.templateParameters != null) {
            if (!element.templateParameters!!.processDeclarations(processor, state, lastParent, place)) {
                return false
            }
        }
        return true

    }

    fun processDeclarations(element: ConditionalStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        var toContinue = true
        for (statement in element.statements) {
            // Don’t processe the declaration twice, we just processed it
            if (lastParent != null && lastParent === statement) {
                continue
            }
            // do not break as there can be multiple definition of the same element based on a certain condition
            if (!statement.processDeclarations(processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        return toContinue
    }

    fun processDeclarations(element: ConditionalDeclaration,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        // Maybe change Psi structure to separate staticIF and version(Foo) forms into 2 different nodes
        var toContinue = true

        // `version(FOO):` form
        if (element.kW_ELSE == null && element.oP_COLON != null) {
            // Our member cannot see other declarations
            for (decl in element.declarations) {
                // don’t process declaration twice, we just processed it
                if (lastParent != null && lastParent === decl) {
                    continue
                }
                if (!decl.processDeclarations(processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
            for (block in element.declarationBlocks) {
                // don’t process declaration twice, we just processed it
                if (lastParent != null && lastParent === block) {
                    continue
                }
                if (!block.processDeclarations(processor, state, lastParent, place)) {
                    toContinue = false
                }
            }
            return toContinue
        }

        // Static If
        // Our member cannot see other declarations
        if (lastParent != null && lastParent.parent === element) {
            return true
        }
        for (decl in element.declarations) {
            // Note: only 2 declarations are possible the one for the if and the one for the else
            if (!decl.processDeclarations(processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        for (block in element.declarationBlocks) {
            // Note: only 2 declarations blocks are possible the one for the if and the one for the else
            if (!block.processDeclarations(processor, state, lastParent, place)) {
                toContinue = false
            }
        }
        return toContinue
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: VersionSpecification,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        return processor.execute(element, state)
    }

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: OutStatement,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        // External elements cannot see our variables
        if (lastParent != null && lastParent.parent !== element)
            return true

        return processor.execute(element, state)
    }

    fun processDeclarations(element: DeclarationBlock,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        var toContinue = true

        // Our children can only access to elements declared before them
        if (lastParent != null && lastParent.parent === element) {
            return PsiScopesUtil.walkChildrenScopes(element, processor, state, lastParent, place)
        }

        for (decl in element.declarations) {
            if (!decl.processDeclarations(processor, state, lastParent, place)) {
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

    @Suppress("UNUSED_PARAMETER")
    fun processDeclarations(element: Parameter,
                            processor: PsiScopeProcessor,
                            state: ResolveState,
                            lastParent: PsiElement?,
                            place: PsiElement): Boolean {
        return processor.execute(element, state)
    }
}
