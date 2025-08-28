package io.github.intellij.dlanguage.psi.impl

import com.intellij.icons.AllIcons
import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.ItemPresentationProviders
import com.intellij.openapi.ui.Queryable
import com.intellij.openapi.util.NlsSafe
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.*
import com.intellij.psi.impl.PsiElementBase
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.ui.IconManager
import com.intellij.util.IncorrectOperationException
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.psi.DlangPsiFile
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.named.DLanguageModule
import io.github.intellij.dlanguage.psi.named.DLanguagePackage
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributesFinder
import javax.swing.Icon

class DLanguageModuleImpl(private val manager: PsiManager, private val file: DlangPsiFile) : PsiElementBase(),
    DLanguageModule, Queryable {

    private fun findPackage(qName: String): DLanguagePackage? {
        return null
    }

    private fun allScope(): GlobalSearchScope =
        TODO("Not yet implemented")


    override fun getQualifiedName(): @NlsSafe String = file.getFullyQualifiedModuleName()

    override fun getParentPackage(): DLanguagePackage? =
        findPackage(StringUtil.getPackageName(qualifiedName))

    override fun getFile(searchScope: GlobalSearchScope): PsiFile = file

    override fun getName(): String = file.getModuleName()

    override fun getAttributes(): DAttributes? = null

    override fun isPublic(): Boolean = true

    override fun isProtected(): Boolean = false

    override fun isPrivate(): Boolean = false

    override fun visibility(): DAttributesFinder.Visibility = DAttributesFinder.Visibility.PUBLIC

    override fun isProperty(): Boolean = false

    override fun isNoGC(): Boolean = false

    override fun isExtern(): Boolean = false

    override fun isPure(): Boolean = false

    override fun isNothrow(): Boolean = false

    override fun isConst(): Boolean = false

    override fun isImmutable(): Boolean = false

    override fun isEnum(): Boolean = false

    override fun setName(name: @NlsSafe String): PsiElement? {
        return DLanguageModuleImpl(manager, file.setName(name) as DlangPsiFile)
    }

    override fun checkSetName(name: String?) {
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is DlangVisitor)
            visitor.visitModule(this)
        else
            visitor.visitElement(this)
    }

    override fun getElementIcon(flags: Int): Icon {
        return IconManager.getInstance().createLayeredIcon(this, AllIcons.Nodes.Package, flags)
    }

    override fun getPresentation(): ItemPresentation? {
        return ItemPresentationProviders.getItemPresentation(this)
    }

    override fun navigate(requestFocus: Boolean) {
        file.navigate(requestFocus)
    }

    override fun getLanguage(): Language = DLanguage

    override fun getChildren(): Array<out PsiElement> = EMPTY_ARRAY

    override fun getParent(): PsiElement? = parentPackage

    override fun getContainingFile(): PsiFile? = null

    override fun getTextRange(): TextRange? = null

    override fun getStartOffsetInParent(): Int = -1

    override fun getTextLength(): Int = -1

    override fun findElementAt(offset: Int): PsiElement? = null

    override fun getTextOffset(): Int = -1

    override fun getText(): @NlsSafe String? = null

    override fun textToCharArray(): CharArray {
        throw UnsupportedOperationException()
    }

    override fun getNode(): ASTNode? = null

    override fun textMatches(text: CharSequence): Boolean {
        throw IncorrectOperationException()
    }

    override fun textMatches(element: PsiElement): Boolean {
        throw IncorrectOperationException()
    }

    override fun copy(): PsiElement? = null

    override fun add(element: PsiElement): PsiElement {
        throw IncorrectOperationException()
    }

    override fun addBefore(element: PsiElement, anchor: PsiElement?): PsiElement {
        throw IncorrectOperationException()
    }

    override fun addAfter(element: PsiElement, anchor: PsiElement?): PsiElement {
        throw IncorrectOperationException()
    }

    override fun checkAdd(element: PsiElement) {
        throw IncorrectOperationException()
    }

    override fun delete() {
        checkDelete()
        file.delete()
    }

    override fun checkDelete() {
        file.checkDelete()
    }

    override fun replace(newElement: PsiElement): PsiElement {
        throw IncorrectOperationException()
    }

    override fun isWritable(): Boolean {
        return file.isWritable
    }

    override fun toString(): String = "DLanguagePackage:$qualifiedName"

    override fun isValid(): Boolean = file.isValid

    override fun canNavigate(): Boolean = isValid

    override fun canNavigateToSource(): Boolean = false

    override fun isPhysical(): Boolean = true

    override fun putInfo(info: MutableMap<in String, in String>) {
        info["packageName"] = name
        info["packageQualifiedName"] = qualifiedName
    }

    override fun getManager(): PsiManager = manager

    override fun clone(): Any {
        return super.clone()
    }

    override fun equals(other: Any?): Boolean {
        return other is DLanguageModuleImpl
            && manager == other.manager
            && qualifiedName == other.qualifiedName
    }

    override fun isEquivalentTo(another: PsiElement?): Boolean {
        if (another is DlangPsiFile)
            return file == another
        return super.isEquivalentTo(another)
    }

    override fun hashCode(): Int = qualifiedName.hashCode()

    override fun getNameIdentifier(): PsiElement? = null

    override fun processDeclarations(
        processor: PsiScopeProcessor,
        state: ResolveState,
        lastParent: PsiElement?,
        place: PsiElement
    ): Boolean {
        return file.processDeclarations(processor, state, lastParent, place)
    }

}
