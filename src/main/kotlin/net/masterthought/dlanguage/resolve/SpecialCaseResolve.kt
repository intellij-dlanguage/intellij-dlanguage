package net.masterthought.dlanguage.resolve

import com.google.common.collect.Sets
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.impl.DirectoryIndex
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.impl.file.PsiDirectoryFactory
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import net.masterthought.dlanguage.index.DModuleIndex
import net.masterthought.dlanguage.psi.DLanguageFile
import net.masterthought.dlanguage.psi.DLanguageIdentifier
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.psi.references.DReference
import net.masterthought.dlanguage.stubs.index.DTopLevelDeclarationIndex
import net.masterthought.dlanguage.utils.*

object SpecialCaseResolve {
    /**
     * couple possibilities:
     * in an import
     *  - a package in an import
     *  - a module in an import
     *  - a scope in an import
     * in a module specification
     *  - a package in a module
     *  - a module in a module
     */
    fun findDefinitionNode(e: Identifier): Set<PsiNamedElement> {

        if (inModuleDeclaration(e) != null) {
            val identifiers = inModuleDeclaration(e)!!.identifierChain!!.identifiers
            if (identifiers.last() == e) {
                return resolveModule(inModuleDeclaration(e)!!.identifierChain!!)
            }
            return resolvePackage(identifiers.subList(0, identifiers.indexOf(e) + 1))
        }
        if (inSingleImport(e) != null) {
            val identifiers = inSingleImport(e)!!.identifierChain!!.identifiers
            if (identifiers.last() == e) {
                return resolveModule(inSingleImport(e)!!.identifierChain!!)
            }
            return resolvePackage(identifiers.subList(0, identifiers.indexOf(e) + 1))
        }
        if (inImportBind(e) != null) {
            return (inImportBind(e)!!.parent as ImportDeclaration).singleImports.flatMap { resolveScopedSymbol(it, e) }.toSet()

        }
        return emptySet()
    }

    private fun resolvePackage(parents: MutableList<DLanguageIdentifier>): Set<PsiNamedElement> {
        if (parents.size == 0)
            return emptySet()
        val last = parents.last()
        var name = ""
        for (parent in parents) {
            if (parent != last) {
                name += (parent.name + ".")
            } else {
                name += parent.name
            }
        }
        val project = last.project
        return resolvePackageFromName(project, name)
    }

    private fun resolvePackageFromName(project: Project, name: String): Set<PsiDirectory> {
        return DirectoryIndex.getInstance(project)
            .getDirectoriesByPackageName(name, true)
            .findAll()
            .map { PsiDirectoryFactory.getInstance(project).createDirectory(it) }
            .toSet()
    }


    private fun resolveModule(path: IdentifierChain): Set<PsiNamedElement> {
        return Sets.newHashSet(DModuleIndex.getFilesByModuleName(path.project, path.text, GlobalSearchScope.allScope(path.project)))
    }

    private fun resolveScopedSymbol(singleImport: SingleImport, scope: DLanguageIdentifier): Set<PsiNamedElement> {
        val project = scope.project
        val resolved = mutableSetOf<PsiNamedElement>()
        for (resolveResult in (singleImport.identifierChain!!.identifiers.last().reference as DReference).multiResolve(false)) {
            assert(resolveResult.element is DLanguageFile)
            for (file in DResolveUtil.getInstance(project).getAllImportedModulesAsFiles(resolveResult.element as DLanguageFile)) {
                resolved.addAll(StubIndex.getElements(DTopLevelDeclarationIndex.KEY, scope.name, project, GlobalSearchScope.fileScope(file), DNamedElement::class.java))
            }
            resolved.addAll(StubIndex.getElements(DTopLevelDeclarationIndex.KEY, scope.name, project, GlobalSearchScope.fileScope(resolveResult.element as DLanguageFile), DNamedElement::class.java))
        }
        return resolved
    }

    fun inModuleDeclaration(e: DNamedElement): ModuleDeclaration? {
        return PsiTreeUtil.getTopmostParentOfType(e, ModuleDeclaration::class.java)
    }

    fun inSingleImport(identifier: Identifier): SingleImport? {
        return PsiTreeUtil.getTopmostParentOfType(identifier, SingleImport::class.java)
    }


    fun isApplicable(e: PsiNamedElement): Boolean {
        if (e !is Identifier) {
            return false
        }
        return (inModuleDeclaration(e) != null || inSingleImport(e) != null || inImportBind(e) != null)
    }

    private fun inImportBind(identifier: Identifier): ImportBindings? {
        return PsiTreeUtil.getTopmostParentOfType(identifier, ImportBindings::class.java)
    }

    fun tryPackageResolve(e: Identifier, profile: Boolean = false): Set<PsiNamedElement> {
        fun inIdentifierOrTemplateChain(identifier: Identifier): IdentifierOrTemplateChain? {
            return PsiTreeUtil.getTopmostParentOfType(identifier, IdentifierOrTemplateChain::class.java)
        }

        fun inIdentifierOrTemplateInstance(identifier: Identifier): IdentifierOrTemplateInstance? {
            return PsiTreeUtil.getTopmostParentOfType(identifier, IdentifierOrTemplateInstance::class.java)
        }

        if (inIdentifierOrTemplateChain(e) != null) {
            val instances = inIdentifierOrTemplateChain(e)!!.identifierOrTemplateInstances
            val endIndex = instances.indexOf(inIdentifierOrTemplateInstance(e))
            val parents = instances.subList(0, endIndex + 1)
            if (parents.size == 0) {
                return emptySet()
            }
            val last = parents.last()
            var name = ""
            for (parent in parents) {
                if (parent != last) {
                    name += (parent.text + ".")
                } else {
                    name += parent.text
                }
            }
            val directoryResolve = resolvePackageFromName(e.project, name)
            return directoryResolve//todo do a file resolve as well
        }
        return emptySet()
    }

}
