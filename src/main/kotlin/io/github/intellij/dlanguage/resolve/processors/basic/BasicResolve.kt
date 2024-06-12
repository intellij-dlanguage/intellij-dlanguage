package io.github.intellij.dlanguage.resolve.processors.basic

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.ResolveState
import com.intellij.psi.search.GlobalSearchScope.allScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import io.github.intellij.dlanguage.attributes.DNameScopeProcessor
import io.github.intellij.dlanguage.index.DModuleIndex
import io.github.intellij.dlanguage.psi.DlangPsiFile
import io.github.intellij.dlanguage.psi.DlangTypes


/**
 * Created by francis on 7/24/2017.
 */
class BasicResolve private constructor(val project: Project, val profile: Boolean = false) {

    companion object {
        val log: Logger = Logger.getInstance(this::class.java)

        private val basicResolves: MutableMap<Project, BasicResolve> = mutableMapOf()

        fun getInstance(project: Project, profile: Boolean = false): BasicResolve {
            return basicResolves.getOrPut(project) { BasicResolve(project, profile) }
        }
    }

    val `object`: DlangPsiFile?
        get() = DModuleIndex.getFilesByModuleName(project, "object", allScope(project)).toSet().firstOrNull()?.containingFile as DlangPsiFile?

    fun findDefinitionNode(e: PsiElement): Set<PsiNamedElement> {
        //todo fix templated functions return type bug
        if (e.elementType != DlangTypes.ID) {
            return emptySet()
        }

        val startTime = System.currentTimeMillis()
        val nameProcessor = DNameScopeProcessor(e, profile)
        PsiTreeUtil.treeWalkUp(nameProcessor, e, e.containingFile, ResolveState.initial())
        if (`object` != null) {
            PsiTreeUtil.treeWalkUp(nameProcessor, `object`!!, `object`, ResolveState.initial())
        }
        val end = System.currentTimeMillis()
        if (profile) {
            log.info("Scope processor took:" + (end - startTime))
        }
        return nameProcessor.result
    }

}
