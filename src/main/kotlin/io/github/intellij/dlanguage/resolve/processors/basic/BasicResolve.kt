package io.github.intellij.dlanguage.resolve.processors.basic

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.ResolveState
import com.intellij.psi.search.GlobalSearchScope.allScope
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.attributes.DNameScopeProcessor
import io.github.intellij.dlanguage.index.DModuleIndex.*
import io.github.intellij.dlanguage.psi.DlangFile
import io.github.intellij.dlanguage.utils.Identifier


/**
 * Created by francis on 7/24/2017.
 */
class BasicResolve private constructor(val project: Project, val profile: Boolean = false) {

    companion object {
        private val basicResolves: MutableMap<Project, BasicResolve> = mutableMapOf()
        //
        fun getInstance(project: Project, profile: Boolean = false): BasicResolve {
            return basicResolves.getOrPut(project, { BasicResolve(project, profile) })
        }
    }

    val log: Logger = Logger.getInstance(this::class.java)

    val `object`: DlangFile?
        get() = getFilesByModuleName(project, "object", allScope(project)).toSet().singleOrNull()?.containingFile as DlangFile?

    fun findDefinitionNode(e: PsiNamedElement): Set<PsiNamedElement> {
        //todo fix templated functions return type bug
        if (e !is Identifier) {
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
