package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import io.github.intellij.dlanguage.psi.DLanguageGotoStatement
import io.github.intellij.dlanguage.psi.references.LabelReference

abstract class DLanguageGotoStatementImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageGotoStatement {
    override fun getReference(): PsiReference? {
        if (identifier == null) {
            return null
        }
        return LabelReference(this, identifier!!.textRangeInParent)
    }
}
