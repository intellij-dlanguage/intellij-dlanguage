package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import io.github.intellij.dlanguage.psi.DLanguageIdentifierChain
import io.github.intellij.dlanguage.psi.references.DReference

abstract class DLanguageIdentifierChainImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageIdentifierChain {

    override fun getReference(): PsiReference? {
        if (identifier == null) {
            return null
        }
        return DReference(this, identifier!!.textRangeInParent, identifierChain, identifier!!.text)
    }
}
