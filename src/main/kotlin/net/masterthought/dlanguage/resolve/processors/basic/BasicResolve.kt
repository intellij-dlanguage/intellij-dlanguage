package net.masterthought.dlanguage.resolve.processors.basic

import com.google.common.collect.Sets
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.ResolveState
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.GlobalSearchScope.EMPTY_SCOPE
import com.intellij.psi.search.GlobalSearchScope.fileScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import net.masterthought.dlanguage.index.DModuleIndex.getFilesByModuleName
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.resolve.DResolveUtil
import net.masterthought.dlanguage.resolve.processors.DNameScopeProcessor
import net.masterthought.dlanguage.stubs.index.DTopLevelDeclarationIndex
import net.masterthought.dlanguage.utils.Constructor
import net.masterthought.dlanguage.utils.Identifier
import net.masterthought.dlanguage.utils.SingleImport

/**
 * Created by francis on 7/24/2017.
 */
object BasicResolve {


    fun findDefinitionNode(project: Project, e: PsiNamedElement): Set<PsiNamedElement> {
        //todo fix templated functions return type bug


        if (e !is Identifier) {
            return emptySet()
        }

        val nameProcessor = DNameScopeProcessor(e)
        PsiTreeUtil.treeWalkUp(nameProcessor, e, e.containingFile, ResolveState.initial())
        if (nameProcessor.result.size != 0) {
            return nameProcessor.result
        }

//        val start = System.currentTimeMillis()
        val modules = DResolveUtil.getAllImportedModules(e)
//        val end = System.currentTimeMillis()
//        Logger.getInstance(this::class.java).info("modules took:" + (end - start))

        val result = mutableSetOf<PsiNamedElement>()
        // find definition in imported files
        var scope = EMPTY_SCOPE
        for (module in modules) {
            for (file in getFilesByModuleName(project, module, GlobalSearchScope.allScope(project))) {
                scope = scope.uniteWith(fileScope(file))
            }
        }
        result.addAll(StubIndex.getElements(DTopLevelDeclarationIndex.KEY, e.name, e.project, scope, DNamedElement::class.java))
        val finalResult = mutableSetOf<PsiNamedElement>()
        for (element in result) {
            if (element is Constructor) {
                if (DResolveUtil.resolvingConstructor(e) != null) {
                    finalResult.add(element)
                } else continue
            }
            if (DResolveUtil.resolvingConstructor(e) == null) {
                finalResult.add(element)
            }
        }
        return finalResult
    }

}
