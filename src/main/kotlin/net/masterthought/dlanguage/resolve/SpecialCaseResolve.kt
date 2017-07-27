package net.masterthought.dlanguage.resolve

import com.google.common.collect.Sets
import com.intellij.openapi.roots.impl.DirectoryIndex
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.impl.file.PsiDirectoryFactory
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import net.masterthought.dlanguage.DLanguage
import net.masterthought.dlanguage.index.DModuleIndex
import net.masterthought.dlanguage.psi.DLanguageFile
import net.masterthought.dlanguage.psi.DLanguageIdentifier
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.psi.references.DReference
import net.masterthought.dlanguage.utils.Identifier
import net.masterthought.dlanguage.utils.IdentifierChain
import net.masterthought.dlanguage.utils.ModuleDeclaration
import net.masterthought.dlanguage.utils.SingleImport

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
            val scope = inSingleImport(e)?.identifier
            if (scope != null && scope == e) {
                return resolveScopedSymbol(inSingleImport(e)!!, scope)
            }
            if (identifiers.last() == e) {
                return resolveModule(inSingleImport(e)!!.identifierChain!!)
            }
            return resolvePackage(identifiers.subList(0, identifiers.indexOf(e) + 1))
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
        return DirectoryIndex.getInstance(last.project)
                        .getDirectoriesByPackageName(name, true)
                        .findAll()
                        .map { PsiDirectoryFactory.getInstance(last.project).createDirectory(it) }
                        .toSet()
//        return resolvePackageImpl(parents.subList(0, parents.size - 1), candidates, last.project)
    }

//    private fun resolvePackageImpl(parents: MutableList<DLanguageIdentifier>, candidates: MutableCollection<VirtualFile>, project: Project): Set<PsiNamedElement> {
//
//        val candidatesPsi = candidates
//            .map { PsiDirectoryFactory.getInstance(project).createDirectory(it) }
//        if (candidates.isEmpty() || candidates.size == 1 || parents.size == 0) {
//            return candidatesPsi
//                .toSet()
//        }
//        val last = parents.last()
//        val filtered = candidatesPsi.filter { it.parentDirectory?.name == last.name }
//        if (filtered.isEmpty()) {
//            return candidatesPsi.toSet()
//        } else {
//            return resolvePackageImpl(parents.subList(0, parents.lastIndex - 1), Sets.newHashSet(filtered.map { it.virtualFile }), project)
//        }
//    }

    private fun resolveModule(path: IdentifierChain): Set<PsiNamedElement> {
        return Sets.newHashSet(DModuleIndex.getFilesByModuleName(path.project, path.text, GlobalSearchScope.allScope(path.project)))
    }

    private fun resolveScopedSymbol(singleImport: SingleImport, scope: DLanguageIdentifier): Set<PsiNamedElement> {
        for (resolveResult in (singleImport.identifierChain!!.identifiers.last().reference as DReference).multiResolve(false)) {
            assert(resolveResult.element is DLanguageFile)

        }
        TODO()
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
        return (inModuleDeclaration(e) != null || inSingleImport(e) != null)

    }

}
