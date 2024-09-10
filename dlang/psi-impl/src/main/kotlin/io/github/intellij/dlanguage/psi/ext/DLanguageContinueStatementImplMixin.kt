package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import io.github.intellij.dlanguage.psi.DLanguageBreakStatement
import io.github.intellij.dlanguage.psi.DLanguageContinueStatement
import io.github.intellij.dlanguage.psi.DLanguageGotoStatement
import io.github.intellij.dlanguage.psi.references.LabelReference

abstract class DLanguageContinueStatementImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageContinueStatement {
    override fun getReference(): PsiReference? {
        if (identifier == null) {
            return null
        }
        return LabelReference(this, identifier!!.textRangeInParent)
    }
}
