package io.github.intellij.dlanguage.attributes

import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.stubs.NamedStubBase
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.resolve.processors.DResolveProcessor
import io.github.intellij.dlanguage.stubs.index.DMembersIndex
import io.github.intellij.dlanguage.stubs.index.DPublicImportIndex
import io.github.intellij.dlanguage.stubs.index.DTopLevelDeclarationIndex
import io.github.intellij.dlanguage.utils.Constructor
import io.github.intellij.dlanguage.utils.Identifier
import io.github.intellij.dlanguage.utils.SingleImport

/**
 * Created by francis on 6/15/2017.
 */
class DNameScopeProcessor(var start: Identifier, val profile: Boolean = false) : DResolveProcessor<DNamedElement, DNamedElement> {
    override fun matches(call: DNamedElement, decl: DNamedElement): Boolean {
        return true
    }

    val project = start.project
    val log: Logger = Logger.getInstance(this::class.java)
    val name = start.name

    override val result = mutableSetOf<DNamedElement>()

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        var toContinue = true
        val startTime = System.currentTimeMillis()
        if (element is DNamedElement) {
            if (element.name == name) {
                if (element !is Constructor) {//todo this class should be renamed because of this
                    result.add(element)
                    toContinue = false
                }
            }
            if (element is SingleImport) {
                return handleImport(element)
            }
        } else {
            throw IllegalArgumentException()
        }
        val endTime = System.currentTimeMillis()
        if ((endTime - startTime) > 30)
            if (profile)
                log.info("execute took:" + (endTime - startTime))
        return toContinue
    }

//    val importsDoneKey = Key.create<MutableSet<SingleImport>>("currentlyProcessedImports")

    val currentlyAlreadyDone: MutableSet<SingleImport> = mutableSetOf()

    //returns false if results are found, true if more searching is needed b/c nothing was found
    private fun handleImport(import: SingleImport): Boolean {
        if (currentlyAlreadyDone.contains(import))
            return true
        if (import.importedModuleName == "")
            throw IllegalArgumentException()
        val startSize = result.size
        val imports = DPublicImportIndex.recursivelyGetAllPublicImports(import)
        if (import.applicableImportBinds.size == 0) {
            result.addAll(DTopLevelDeclarationIndex.getTopLevelSymbols(start.name, import.importedModuleName, project))
        } else {
            //todo this can be done better:
            val bindDecls = import.applicableImportBinds.flatMap { DTopLevelDeclarationIndex.getTopLevelSymbols(it, import.importedModuleName, project) }
            if (!bindDecls.filter { it.name == start.name }.isEmpty()) {
                result.addAll(bindDecls.filter { it.name == start.name })
                return false
            }
            val bindDeclsMembers = import.applicableImportBinds.flatMap { DMembersIndex.getMemberSymbols(it, import.importedModuleName, project) }
            if (!bindDeclsMembers.filter { it.name == start.name }.isEmpty()) {
                result.addAll(bindDeclsMembers.filter { it.name == start.name })
                return false
            }
        }
        currentlyAlreadyDone.add(import)//needs to be before for imports loop in case of an infinite import loop
        for (recursivelyImported in imports) {
            handleImport(recursivelyImported)
        }
        return result.size == startSize
    }

    private fun getMembersOfBind(resolveResult: DNamedElement) {
        for (member: NamedStubBase<*> in (resolveResult as io.github.intellij.dlanguage.psi.interfaces.HasMembers<*>).members) {
            if (member.name == start.name) {
                result.add((member.psi as DNamedElement))
            }
        }
    }

}
