package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import io.github.intellij.dlanguage.psi.DLanguageTemplateSingleArgument
import io.github.intellij.dlanguage.psi.references.TypeInstanceReference

abstract class DLanguageTemplateSingleArgumentImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
 DLanguageTemplateSingleArgument {

    override fun getReference(): PsiReference? {
        if (identifier != null) {
            return TypeInstanceReference(this, identifier!!.textRangeInParent)
        }
        return null
    }
}
