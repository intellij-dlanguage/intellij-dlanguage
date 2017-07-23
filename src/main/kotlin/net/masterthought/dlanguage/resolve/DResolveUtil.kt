package net.masterthought.dlanguage.resolve

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.ResolveState
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex.getElements
import com.intellij.psi.util.PsiTreeUtil
import net.masterthought.dlanguage.index.DModuleIndex.getFilesByModuleName
import net.masterthought.dlanguage.processors.*
import net.masterthought.dlanguage.psi.DLanguageTypes.*
import net.masterthought.dlanguage.psi.DPsiUtil
import net.masterthought.dlanguage.psi.DPsiUtil.getParent
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.stubs.index.DTopLevelDeclarationIndex
import net.masterthought.dlanguage.utils.*

/**
 * Created by francis on 5/12/17.
 */
object DResolveUtil {
    enum class ResolveType {
        FUNCTION, CONSTRUCTOR, ALL
    }


    fun inSingleImport(identifier: Identifier): SingleImport? {
        return PsiTreeUtil.getTopmostParentOfType(identifier, SingleImport::class.java)
    }

    fun inFunctionUsage(identifier: Identifier): FunctionCallExpression? {
        return getParent(identifier, setOf(FUNCTION_CALL_EXPRESSION), setOf(EXPRESSION, ARGUMENTS)) as FunctionCallExpression?
    }

    fun findDefinitionNode(project: Project, e: PsiNamedElement): List<PsiNamedElement> {
        //todo fix templated functions return type bug
        if (e !is Identifier) {
            return emptyList()
        }
        if (inSingleImport(e) != null) {
            return getFilesByModuleName(project, inSingleImport(e)!!.name, GlobalSearchScope.allScope(project))
        }

        val resolveType: ResolveType
        val processor: DResolveProcessor<Any, DNamedElement>
        if (inFunctionUsage(e) != null) {
            processor = DFunctionProcessor(e, inFunctionUsage(e)!!) as DResolveProcessor<Any, DNamedElement>
            PsiTreeUtil.treeWalkUp(processor, e, e.containingFile, ResolveState.initial())
            resolveType = ResolveType.FUNCTION
        } else if (resolvingConstructor(e) != null) {
            processor = DConstructorProcessor(e, resolvingConstructor(e)!!) as DResolveProcessor<Any, DNamedElement>
            PsiTreeUtil.treeWalkUp(processor, e, e.containingFile, ResolveState.initial())
            resolveType = ResolveType.CONSTRUCTOR
        } else {
            processor = DNameScopeProcessor(e) as DResolveProcessor<Any, DNamedElement>
            PsiTreeUtil.treeWalkUp(processor, e, e.containingFile, ResolveState.initial())
            resolveType = ResolveType.ALL
        }
        if (processor.result.isNotEmpty()) {
            return processor.result.toList()
        }

        return findDefinitionNodeFromImports(e, project, resolveType)
    }

    private fun findDefinitionNodeFromImports(e: Identifier, project: Project, resolveType: ResolveType): List<PsiNamedElement> {
        val importProcessor = DImportScopeProcessor()
        PsiTreeUtil.treeWalkUp(importProcessor, e, e.containingFile, ResolveState.initial())
        val modules: MutableList<String> = mutableListOf()

        (importProcessor.imports).mapTo(modules) { it.name }
        val result = mutableSetOf<PsiNamedElement>()
        // find definition in imported files
        for (module in modules) {
            //todo use only stub tree to imporove performance here and maube not make evewry identifier a stub
            val files = getFilesByModuleName(project, module, GlobalSearchScope.allScope(project))
            for (f in files) {
                for (element in getElements(DTopLevelDeclarationIndex.KEY, e.name, e.project, GlobalSearchScope.fileScope(f), DNamedElement::class.java)) {
                    when (resolveType) {
                        ResolveType.FUNCTION -> {
                            if (element is FunctionDeclaration) {
                                if (DFunctionProcessor(e, inFunctionUsage(e)!!).matches(inFunctionUsage(e)!!, element)) {
                                    result.add(element)
                                }
                            }
                        }
                        ResolveType.CONSTRUCTOR -> {
                            if (element is Constructor) {
                                if (DConstructorProcessor(e, resolvingConstructor(e)!!).matches(resolvingConstructor(e)!!, element)) {
                                    result.add(element)
                                }
                            }
                        }
                        ResolveType.ALL -> {
                            result.add(element)
                        }
                    }
                }
            }
        }
        return result.toList()
    }

    fun resolvingConstructor(e: PsiElement): NewExpression? {
        return DPsiUtil.getParent(e, setOf(NEW_EXPRESSION), setOf(BLOCK_STATEMENT, STRUCT_BODY, DECLARATION)) as NewExpression?
    }

}
