package net.masterthought.dlanguage.resolve

import com.intellij.openapi.diagnostic.RuntimeExceptionWithAttachments
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.ResolveState
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import net.masterthought.dlanguage.index.DModuleIndex.getFilesByModuleName
import net.masterthought.dlanguage.processors.DImportScopeProcessor
import net.masterthought.dlanguage.processors.DNameScopeProcessor
import net.masterthought.dlanguage.psi.DLanguageNewExpression
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.stubs.index.DTopLevelDeclarationIndex
import net.masterthought.dlanguage.utils.Constructor
import net.masterthought.dlanguage.utils.Identifier
import net.masterthought.dlanguage.utils.NewAnonClassExpression
import net.masterthought.dlanguage.utils.SingleImport

/**
 * Created by francis on 5/12/17.
 */
object DResolveUtil {
    fun findDefinitionNode(project: Project, e: PsiNamedElement): List<PsiNamedElement> {
        //todo fix templated functions return type bug
        fun inSingleImport(e: Identifier): SingleImport? {
            return PsiTreeUtil.getTopmostParentOfType(e, SingleImport::class.java)
        }

        if (e !is Identifier) {
            return emptyList()
        }

        if (inSingleImport(e) != null) {
            return getFilesByModuleName(project, inSingleImport(e)!!.name, GlobalSearchScope.allScope(project))
        }

        val nameProcessor = DNameScopeProcessor(e)
        PsiTreeUtil.treeWalkUp(nameProcessor, e, e.containingFile, ResolveState.initial())
        if (nameProcessor.result.size != 0) {
            return nameProcessor.result.toList()
        }

        val importProcessor = DImportScopeProcessor()
        PsiTreeUtil.treeWalkUp(importProcessor, e, e.containingFile, ResolveState.initial())
        val modules: MutableList<String> = mutableListOf()
        (importProcessor.imports).filterNotNull().mapTo(modules) { it.name }

        val result = mutableSetOf<PsiNamedElement>()
        // find definition in imported files
        for (module in modules) {
            val files = getFilesByModuleName(project, module, GlobalSearchScope.allScope(project))
            for (f in files) {
                try {
                    result.addAll(StubIndex.getElements(DTopLevelDeclarationIndex.KEY, e.name, e.project, GlobalSearchScope.fileScope(f), DNamedElement::class.java))
                } catch(ex: RuntimeExceptionWithAttachments) {
                    throw ex
                }
            }
        }
        val finalResult = mutableSetOf<PsiNamedElement>()
        for (element in result) {
            if (element is Constructor) {
                if (resolvingConstructor(e)) {
                    finalResult.add(element)
                } else continue
            }
            if (!resolvingConstructor(e)) {
                finalResult.add(element)
            }
        }
        return finalResult.toList()
    }

    fun resolvingConstructor(e: PsiElement): Boolean {
        var parent: PsiElement? = e.parent
        while (true) {
            if (parent == null)
                break
            if (parent is DLanguageNewExpression || parent is NewAnonClassExpression)
                return true
            parent = parent.parent
        }
        return false
    }

}
