package io.github.intellij.dlanguage.resolve

import com.google.common.collect.Sets.newHashSet
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.impl.DirectoryIndex
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.impl.file.PsiDirectoryFactory
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.index.DModuleIndex
import io.github.intellij.dlanguage.psi.named.DlangIdentifier
import io.github.intellij.dlanguage.stubs.index.DPublicImportIndex
import io.github.intellij.dlanguage.stubs.index.DTopLevelDeclarationIndex
import io.github.intellij.dlanguage.utils.*
import java.util.stream.Collectors

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
     * in a version condition
     * need to resolve a label
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
            return (inImportBind(e)!!.parent as ImportDeclaration).singleImports.flatMap { resolveScopedSymbol(it, e.name, e.project) }.toSet()

        }
//        if(inVersionCondition(e) != null){
//            return resolveVersion(e)
//        }
        if (inPackageAttribute(e)) {
            val chain = e.parent as IdentifierChain
            val identifiers = chain.identifiers
            if (identifiers.last() == e) {
                return resolveModule(chain)
            }
            return resolvePackage(identifiers.subList(0, identifiers.indexOf(e) + 1))
        }
        if (resolvingLabel(e)) {
            return resolveLabel(e)
        }
        return emptySet()
    }

//    private fun inVersionCondition(identifier: Identifier): VersionCondition? {
//        return PsiTreeUtil.getTopmostParentOfType(identifier, VersionCondition::class.java)
//    }

    private fun resolveLabel(e: Identifier): Set<PsiNamedElement> {
        val functionDeclaration = PsiTreeUtil.getTopmostParentOfType(e, FunctionDeclaration::class.java)
        if (functionDeclaration == null)
            return emptySet()
        val out = mutableSetOf<LabeledStatement>()
        for (labeledStatement in PsiTreeUtil.findChildrenOfType(functionDeclaration, LabeledStatement::class.java)) {
            if (labeledStatement.name.equals(e.name))
                out.add(labeledStatement)
        }
        return out
    }

    private fun resolvePackage(parents: List<DlangIdentifier>): Set<PsiNamedElement> {
        if (parents.isEmpty())
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

    private fun resolveModule(path: QualifiedIdentifier): Set<PsiNamedElement> {
        return newHashSet(DModuleIndex.getFilesByModuleName(path.project, path.text, GlobalSearchScope.allScope(path.project)))
    }

    private fun resolveModule(path: IdentifierChain): Set<PsiNamedElement> {
        return newHashSet(DModuleIndex.getFilesByModuleName(path.project, path.importText, GlobalSearchScope.allScope(path.project)))
    }

    private fun resolveScopedSymbol(import: SingleImport, scope: String, project: Project): Set<PsiNamedElement> {
        val res = DTopLevelDeclarationIndex.getTopLevelSymbols(scope, import.importedModuleName, project)
        for (publicImport in DPublicImportIndex.recursivelyGetAllPublicImports(import)) {
            res.addAll(DTopLevelDeclarationIndex.getTopLevelSymbols(scope, publicImport.importedModuleName, project))
        }
        // A constructor and destructor cannot be imported, so ignore them
        res.removeIf { it is Constructor || it is Destructor }
        return res
    }

    fun inModuleDeclaration(e: io.github.intellij.dlanguage.psi.interfaces.DNamedElement): ModuleDeclaration? {
        return PsiTreeUtil.getTopmostParentOfType(e, ModuleDeclaration::class.java)
    }

    fun inSingleImport(identifier: Identifier): SingleImport? {
        return PsiTreeUtil.getTopmostParentOfType(identifier, SingleImport::class.java)
    }


    fun isApplicable(e: PsiNamedElement): Boolean {
        if (e !is Identifier) {
            return false
        }
        return inModuleDeclaration(e) != null || inSingleImport(e) != null || inImportBind(e) != null || resolvingLabel(e) || inPackageAttribute(e)
    }

    private fun inPackageAttribute(identifier: Identifier): Boolean {
        val attribute = PsiTreeUtil.getTopmostParentOfType(identifier, Attribute::class.java)
        return attribute != null
            && attribute.kW_PACKAGE != null
    }

    private fun resolvingLabel(e: Identifier): Boolean {
        if (e.parent is GotoStatement) {
            return true
        }
        if (e.parent is BreakStatement) {
            return true
        }
        return false
    }

    private fun inImportBind(identifier: Identifier): ImportBindings? {
        return PsiTreeUtil.getTopmostParentOfType(identifier, ImportBindings::class.java)
    }

    fun tryPackageResolve(e: Identifier): Set<PsiNamedElement> {
        fun inMixinQualifiedIdentifier(identifier: Identifier): MixinQualifiedIdentifier? {
            return PsiTreeUtil.getTopmostParentOfType(identifier, MixinQualifiedIdentifier::class.java)
        }

        if (inMixinQualifiedIdentifier(e) != null) {
            val mixinName = inMixinQualifiedIdentifier(e)!!.mixinQualifiedIdentifier
            mixinName?: return emptySet()
            val directoryResolve = resolvePackageFromName(e.project, mixinName.text)
            return directoryResolve//todo do a file resolve as well
        }
        return emptySet()
    }

}

val IdentifierChain.importText: String
    get() {
        return identifiers.stream().map(DlangIdentifier::getText).collect(Collectors.joining("."))
    }
