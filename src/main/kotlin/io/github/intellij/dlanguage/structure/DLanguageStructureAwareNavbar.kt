package io.github.intellij.dlanguage.structure

import com.intellij.ide.navigationToolbar.StructureAwareNavBarModelExtension
import com.intellij.lang.Language
import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.presentation.getPresentationIcon
import io.github.intellij.dlanguage.psi.DLanguageTemplateMixinDeclaration
import io.github.intellij.dlanguage.psi.interfaces.UserDefinedType
import io.github.intellij.dlanguage.psi.named.DLanguageFunctionDeclaration
import io.github.intellij.dlanguage.psi.named.DLanguageTemplateDeclaration
import javax.swing.Icon

class DLanguageStructureAwareNavbar : StructureAwareNavBarModelExtension() {

    override val language: Language get() = DLanguage

    override fun getPresentableText(element: Any?): String? {
        if (element is UserDefinedType)
            return element.name
        if (element is DLanguageFunctionDeclaration) {
            return element.name
        }
        if (element is DLanguageTemplateDeclaration) {
            return element.name
        }
        if (element is DLanguageTemplateMixinDeclaration) {
            return element.identifier?.text
        }
        return null
    }

    override fun getIcon(element: Any?): Icon? {
        return if (element is PsiElement)
            getPresentationIcon(element)
        else
            super.getIcon(element)
    }

    override fun getParent(psiElement: PsiElement?): PsiElement? {
        val parent = super.getParent(psiElement)
        if (parent != null)
            return parent
        return psiElement?.containingFile
    }
}
