package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.psi.DLanguageQualifiedIdentifier
import io.github.intellij.dlanguage.psi.interfaces.Declaration
import io.github.intellij.dlanguage.psi.references.AliasValueReference
import io.github.intellij.dlanguage.psi.references.TypeReference
import io.github.intellij.dlanguage.utils.AliasInitializer
import io.github.intellij.dlanguage.utils.Parameter
import io.github.intellij.dlanguage.utils.TypeSuffix

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
        if (PsiTreeUtil.getParentOfType(this, AliasInitializer::class.java, true, Declaration::class.java, Parameter::class.java) != null) {
            return AliasValueReference(this, range, qualifiedIdentifier, referenceElement.text)
        }
        val inTypeSuffix = PsiTreeUtil.getParentOfType<TypeSuffix>(this, TypeSuffix::class.java, true, Declaration::class.java, Parameter::class.java) != null
        return TypeReference(this, range, qualifiedIdentifier, referenceElement.text, inTypeSuffix)
    }
}
