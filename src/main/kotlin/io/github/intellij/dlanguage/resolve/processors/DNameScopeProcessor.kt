package io.github.intellij.dlanguage.resolve.processors

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.stubs.NamedStubBase
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.interfaces.HasMembers
import io.github.intellij.dlanguage.stubs.index.DMembersIndex
import io.github.intellij.dlanguage.stubs.index.DPublicImportIndex
import io.github.intellij.dlanguage.stubs.index.DTopLevelDeclarationIndex
import io.github.intellij.dlanguage.utils.Constructor
import io.github.intellij.dlanguage.utils.Identifier
import io.github.intellij.dlanguage.utils.SingleImport

/**
 * Created by francis on 6/15/2017.
 */
class DNameScopeProcessor(var start: Identifier, val profile: Boolean = false) : DResolveProcessor<io.github.intellij.dlanguage.psi.interfaces.DNamedElement, io.github.intellij.dlanguage.psi.interfaces.DNamedElement> {
    override fun matches(call: io.github.intellij.dlanguage.psi.interfaces.DNamedElement, decl: io.github.intellij.dlanguage.psi.interfaces.DNamedElement): Boolean {
        return true
    }

    val project = start.project
    val log: Logger = Logger.getInstance(this::class.java)
    val name = start.name

    override val result = mutableSetOf<io.github.intellij.dlanguage.psi.interfaces.DNamedElement>()

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        var toContinue = true
        val startTime = System.currentTimeMillis()
        if (element is io.github.intellij.dlanguage.psi.interfaces.DNamedElement) {
            if (element.name == name) {
                if (element !is Constructor) {//todo this class should be renamed because of this
                    result.add(element)
                    toContinue = false
                }
            }
            if (element is SingleImport) {
                return handleImport(element, state, setOf())
            }
        }
        else{
            throw IllegalArgumentException()
        }
        val endTime = System.currentTimeMillis()
        if ((endTime - startTime) > 30)
            if (profile)
                log.info("execute took:" + (endTime - startTime))
        return toContinue
    }

    val importsDoneKey = Key.create<MutableSet<SingleImport>>("currentlyProcessedImports")

    private fun handleImport(element: SingleImport, state: ResolveState, alreadyDone: Set<SingleImport>): Boolean {
        val currentlyAlreadyDone = mutableSetOf<SingleImport>()
        currentlyAlreadyDone.addAll(alreadyDone)
        if (state.get(importsDoneKey) != null) {
            currentlyAlreadyDone.addAll(state.get(importsDoneKey))
        }
        val imports = DPublicImportIndex.recursivelyGetAllPublicImports(element)
        val startSize = result.size
        if (currentlyAlreadyDone.contains(element))
            return true
        if (element.applicableImportBinds.size == 0) {
            result.addAll(DTopLevelDeclarationIndex.getTopLevelSymbols(start.name, element.importedModuleName, project))
        } else {
            val bindDecls = element.applicableImportBinds.flatMap { DTopLevelDeclarationIndex.getTopLevelSymbols(it, element.importedModuleName, project) }
            if (!bindDecls.filter { it.name == start.name }.isEmpty()) {//todo it appears that for some of these psi is being loaded
                result.addAll(bindDecls.filter { it.name == start.name })
                return false
            }
            val bindDeclsMembers = element.applicableImportBinds.flatMap { DMembersIndex.getMemberSymbols(it, element.importedModuleName, project) }
            if (!bindDeclsMembers.filter { it.name == start.name }.isEmpty()) {
                result.addAll(bindDeclsMembers.filter { it.name == start.name })
                return false
            }
        }
        currentlyAlreadyDone.add(element)
        for (import in imports) {
            if (!currentlyAlreadyDone.contains(import))
                handleImport(import, state, currentlyAlreadyDone)
            currentlyAlreadyDone.add(import)
        }
        state.put(importsDoneKey, currentlyAlreadyDone)
        return result.size == startSize
    }

    private fun getMembersOfBind(resolveResult: io.github.intellij.dlanguage.psi.interfaces.DNamedElement) {
        for (member: NamedStubBase<*> in (resolveResult as io.github.intellij.dlanguage.psi.interfaces.HasMembers<*>).members) {
            if (member.name == start.name) {
                result.add((member.psi as io.github.intellij.dlanguage.psi.interfaces.DNamedElement))
            }
        }
    }

}
