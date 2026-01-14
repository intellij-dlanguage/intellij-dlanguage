package io.github.intellij.dlanguage.features.documentation.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes

class DDocSummarySectionImpl(node: ASTNode): DlangDocPsiElementImpl(node) {
    fun getSection(): PsiElement {
        return findChildByType(DDocElementTypes.DDOC_SECTION_PARAGRAPH)!!
    }
}
