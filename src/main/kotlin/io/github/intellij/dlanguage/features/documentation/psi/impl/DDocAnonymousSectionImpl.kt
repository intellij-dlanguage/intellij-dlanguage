package io.github.intellij.dlanguage.features.documentation.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes

open class DDocAnonymousSectionImpl(node: ASTNode) : DlangDocPsiElementImpl(node) {

    fun getContent(): PsiElement? {
        return findChildByType(DDocElementTypes.DDOC_SECTION_CONTENT)
    }
}
