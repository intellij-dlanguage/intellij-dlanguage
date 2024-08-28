package io.github.intellij.dlanguage.psi.references

import com.google.common.collect.Sets.newHashSet
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.impl.DirectoryIndex
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.*
import com.intellij.psi.impl.file.PsiDirectoryFactory
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.IncorrectOperationException
import com.intellij.util.containers.toArray
import io.github.intellij.dlanguage.index.DModuleIndex
import io.github.intellij.dlanguage.psi.impl.DElementFactory
import io.github.intellij.dlanguage.utils.IdentifierChain
import io.github.intellij.dlanguage.utils.getImportText

class PackageReference(element: IdentifierChain,
                       textRange: TextRange,
) : PsiPolyVariantReferenceBase<IdentifierChain>(element, textRange),
    PsiQualifiedReference {

    override fun multiResolve(incomplete: Boolean): Array<ResolveResult> {
        val result = if (element.parent is IdentifierChain)
            resolvePackage(element)
        else
            resolveModule(element)
        return result.map { PsiElementResolveResult(it) }.toArray(ResolveResult.EMPTY_ARRAY)
    }

    private fun resolveModule(path: IdentifierChain): Set<PsiNamedElement> {
        return newHashSet(DModuleIndex.getFilesByModuleName(path.project, getImportText(path), GlobalSearchScope.allScope(path.project)))
    }

    private fun resolvePackage(parents: IdentifierChain): Set<PsiNamedElement> {
        val project = parents.project
        val text = getImportText(parents)
        return resolvePackageFromName(project, text)
    }

    private fun resolvePackageFromName(project: Project, name: String): Set<PsiDirectory> {
        return DirectoryIndex.getInstance(project)
            .getDirectoriesByPackageName(name, true)
            .map { PsiDirectoryFactory.getInstance(project).createDirectory(it) }
            .toSet()
    }

    override fun handleElementRename(newElementName: String): PsiElement {
        if (myElement.identifier == null)
            throw IncorrectOperationException()
        // Renaming files is tricky: we don't want to change `RenamePsiFileProcessor`,
        // If it’s end by `.d` then it’s because we renamed a file
        val newName = StringUtil.trimEnd(newElementName, ".d")
        myElement.identifier!!.replace(DElementFactory.createDLanguageIdentifierFromText(myElement.project, newName)!!)
        return myElement
    }

    override fun getQualifier(): PsiElement? = myElement.identifierChain

    override fun getReferenceName(): String = myElement.identifier!!.text
}
