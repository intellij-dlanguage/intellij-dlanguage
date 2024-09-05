package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import io.github.intellij.dlanguage.psi.DLanguageImportBind
import io.github.intellij.dlanguage.psi.references.ImportBindReference

abstract class DLanguageImportBindImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageImportBind {
    override fun getReference(): PsiReference? {
        val referenceElement: PsiElement = identifier ?: return null
        val range = referenceElement.textRangeInParent
        return ImportBindReference(this, range)
    }
}
