package io.github.intellij.dlanguage.attributes

import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.stubs.NamedStubBase
import com.jetbrains.rd.util.first
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

    private val project = start.project
    private val log: Logger = Logger.getInstance(this::class.java)
    private val name = start.name

    override val result = mutableSetOf<DNamedElement>()

//    private val importsDoneKey = Key.create<MutableSet<SingleImport>>("currentlyProcessedImports")

    /**
     * Contains the already known (processed) imports (to prevent looping)
     */
    private val currentlyAlreadyDone: MutableSet<SingleImport> = mutableSetOf()

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

    /**
     * Returns false if results are found, true if more searching is needed b/c nothing was found
     */
    private fun handleImport(import: SingleImport): Boolean {
        if (currentlyAlreadyDone.contains(import))
            return true
        if (import.importedModuleName == "")
            throw IllegalArgumentException()

        val startSize = result.size
        val imports = DPublicImportIndex.recursivelyGetAllPublicImports(import)
        if (import.hasImportBinds()) {
            //todo this can be done better:
            val bindDecls = import.applicableImportBinds.flatMap { DTopLevelDeclarationIndex.getTopLevelSymbols(it, import.importedModuleName, project) }
            val matchingBindDeclarations = bindDecls.filter { it.name == start.name }
            if (matchingBindDeclarations.isNotEmpty()) {
                result.addAll(matchingBindDeclarations)
                return false
            }
            val memberDecls = import.applicableImportBinds.flatMap { DMembersIndex.getMemberSymbols(it, import.importedModuleName, project) }
            val matchingMemberDeclarations = memberDecls.filter { it.name == start.name }
            if (matchingMemberDeclarations.isNotEmpty()) {
                result.addAll(matchingMemberDeclarations)
                return false
            }

            // named import binds
            val matchingNamedBindDeclaration = import.applicableNamedImportBinds.filter { it.key == start.name }
            // Check for size == 1 to because otherwise there is a semantic error (duplicate symbol) which is not allowed
            if (matchingNamedBindDeclaration.size == 1) {
                val symbolName = matchingNamedBindDeclaration.first().value
                val namedBindDecls = DTopLevelDeclarationIndex.getTopLevelSymbols(symbolName, import.importedModuleName, project)
                val namedBindDeclsMembers = DMembersIndex.getMemberSymbols(symbolName, import.importedModuleName, project)
                result.addAll(namedBindDeclsMembers)
                result.addAll(namedBindDecls)
                if (namedBindDecls.isNotEmpty() || namedBindDeclsMembers.isNotEmpty())
                    return false
            }
        }

        if (!((isRenamedImport(import) && inSelectiveBind(import)) || (!isRenamedImport(import) && import.hasImportBinds()))) {
            result.addAll(DTopLevelDeclarationIndex.getTopLevelSymbols(start.name, import.importedModuleName, project))
        }
        currentlyAlreadyDone.add(import)//needs to be before for imports loop in case of an infinite import loop
        for (recursivelyImported in imports) {
            handleImport(recursivelyImported)
        }
        return result.size == startSize
    }

    private fun isRenamedImport(import: SingleImport): Boolean {
        return import.identifier != null
    }

    private fun inSelectiveBind(import: SingleImport): Boolean {
        return import.applicableImportBinds.any { it == start.name } ||
            import.applicableNamedImportBinds.any {it.key == start.name }
    }

}
