package net.masterthought.dlanguage.resolve.processors.basic

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.ResolveState
import com.intellij.psi.search.GlobalSearchScope.everythingScope
import com.intellij.psi.util.PsiTreeUtil
import net.masterthought.dlanguage.index.DModuleIndex
import net.masterthought.dlanguage.psi.DLanguageFile
import net.masterthought.dlanguage.resolve.processors.DNameScopeProcessor
import net.masterthought.dlanguage.utils.Identifier

/**
 * Created by francis on 7/24/2017.
 */
class BasicResolve(val project: Project) {

    val `object`: DLanguageFile? = DModuleIndex.getFilesByModuleName(project, "object", everythingScope(project)).toSet().singleOrNull()?.containingFile as DLanguageFile?

    fun findDefinitionNode(e: PsiNamedElement): Set<PsiNamedElement> {
        //todo fix templated functions return type bug
        if (e !is Identifier) {
            return emptySet()
        }

        val nameProcessor = DNameScopeProcessor(e)
        PsiTreeUtil.treeWalkUp(nameProcessor, e, e.containingFile, ResolveState.initial())
        if (`object` != null) {
            PsiTreeUtil.treeWalkUp(nameProcessor, `object`, `object`, ResolveState.initial())
        }
        return nameProcessor.result
    }

}
