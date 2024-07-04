package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import io.github.intellij.dlanguage.psi.DLanguageQualifiedIdentifier
import io.github.intellij.dlanguage.psi.references.DReference

abstract class DLanguageQualifiedIdentifierImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageQualifiedIdentifier {
    override fun getReference(): PsiReference {
        return DReference(this, textRange)
    }
}
