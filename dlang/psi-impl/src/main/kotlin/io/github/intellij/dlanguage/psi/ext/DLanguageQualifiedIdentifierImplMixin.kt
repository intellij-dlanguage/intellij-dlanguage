package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import io.github.intellij.dlanguage.psi.DLanguageQualifiedIdentifier
import io.github.intellij.dlanguage.psi.references.TypeReference

abstract class DLanguageQualifiedIdentifierImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageQualifiedIdentifier {
    override fun getReference(): PsiReference? {
        val referenceElement: PsiElement
        val range: TextRange
        if (identifier != null) {
            referenceElement = identifier!!
            range = referenceElement.textRangeInParent
        } else if (templateInstance != null && templateInstance!!.identifier != null) {
            referenceElement = templateInstance!!.identifier!!
            range = referenceElement.textRangeInParent.shiftRight(templateInstance!!.startOffsetInParent)
        }
        else {
            return null
        }
        return TypeReference(this, range, qualifiedIdentifier, referenceElement.text)
    }
}
