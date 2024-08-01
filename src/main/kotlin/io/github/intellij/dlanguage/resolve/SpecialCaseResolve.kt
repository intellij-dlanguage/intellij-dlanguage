package io.github.intellij.dlanguage.resolve

import com.google.common.collect.Sets.newHashSet
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.impl.DirectoryIndex
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.impl.file.PsiDirectoryFactory
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.index.DModuleIndex
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.interfaces.Statement
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
    fun findDefinitionNode(e: PsiElement): Set<PsiNamedElement> {

        if (inModuleDeclaration(e) != null) {
            val identifiers = inModuleDeclaration(e)!!.identifierChain!!
            if (identifiers == e) {
                return resolveModule(identifiers)
            }
            return resolvePackage(identifiers.identifierChain!!)
        }
        if (inSingleImport(e) != null) {
            val identifiers = inSingleImport(e)!!.identifierChain!!
            if (identifiers == e) {
                return resolveModule(identifiers)
            }
            return resolvePackage(identifiers.identifierChain!!)
        }
        if (inImportBind(e) != null) {
            return (inImportBind(e)!!.parent as ImportDeclaration).singleImports.flatMap { resolveScopedSymbol(it, (e as ImportBind).identifier!!.text, e.project) }.toSet()
        }
//        if(inVersionCondition(e) != null){
//            return resolveVersion(e)
//        }
        if (inPackageAttribute(e)) {
            val topIdentifier = PsiTreeUtil.getTopmostParentOfType(e, IdentifierChain::class.java)
            if (topIdentifier == e) {
                return resolveModule(topIdentifier)
            }
            return resolvePackage(e as IdentifierChain)
        }
        if (resolvingLabel(e)) {
            return resolveLabel(e)
        }
        return emptySet()
    }

//    private fun inVersionCondition(identifier: Identifier): VersionCondition? {
//        return PsiTreeUtil.getTopmostParentOfType(identifier, VersionCondition::class.java)
//    }

    private fun resolveLabel(e: PsiElement): Set<PsiNamedElement> {
        val functionDeclaration = PsiTreeUtil.getTopmostParentOfType(e, FunctionDeclaration::class.java)
        if (functionDeclaration == null)
            return emptySet()
        val out = mutableSetOf<LabeledStatement>()
        for (labeledStatement in PsiTreeUtil.findChildrenOfType(functionDeclaration, LabeledStatement::class.java)) {
            if (labeledStatement.name == e.text)
                out.add(labeledStatement)
        }
        return out
    }

    private fun resolvePackage(parents: IdentifierChain): Set<PsiNamedElement> {
        val project = parents.project
        val text = getImportText(parents)
        return resolvePackageFromName(project, text)
    }

    private fun resolvePackageFromName(project: Project, name: String): Set<PsiDirectory> {
        return DirectoryIndex.getInstance(project)
            .getDirectoriesByPackageName(name, true)
            .findAll()
            .map { PsiDirectoryFactory.getInstance(project).createDirectory(it) }
            .toSet()
    }

    private fun resolveModule(path: IdentifierChain): Set<PsiNamedElement> {
        return newHashSet(DModuleIndex.getFilesByModuleName(path.project, getImportText(path), GlobalSearchScope.allScope(path.project)))
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

    fun inModuleDeclaration(e: PsiElement): ModuleDeclaration? {
        return PsiTreeUtil.getTopmostParentOfType(e, ModuleDeclaration::class.java)
    }

    fun inSingleImport(identifier: PsiElement): SingleImport? {
        return PsiTreeUtil.getTopmostParentOfType(identifier, SingleImport::class.java)
    }


    fun isApplicable(e: PsiElement): Boolean {
        return inModuleDeclaration(e) != null || inSingleImport(e) != null || inImportBind(e) != null || resolvingLabel(e) || inPackageAttribute(e)
    }

    private fun inPackageAttribute(identifier: PsiElement): Boolean {
        val attribute = PsiTreeUtil.getTopmostParentOfType(identifier, Attribute::class.java)
        return attribute != null
            && attribute.kW_PACKAGE != null
    }

    private fun resolvingLabel(e: PsiElement): Boolean {
        if (e.parent is GotoStatement) {
            return true
        }
        if (e.parent is BreakStatement) {
            return true
        }
        return false
    }

    private fun inImportBind(identifier: PsiElement): ImportBindings? {
        return PsiTreeUtil.getTopmostParentOfType(identifier, ImportBindings::class.java)
    }

    fun tryPackageResolve(e: PsiElement): Set<PsiNamedElement> {
        fun inMixinQualifiedIdentifier(identifier: PsiElement): MixinQualifiedIdentifier? {
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
