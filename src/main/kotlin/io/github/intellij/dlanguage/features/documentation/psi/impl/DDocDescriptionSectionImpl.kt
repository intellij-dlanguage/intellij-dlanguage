package io.github.intellij.dlanguage.features.documentation.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes

class DDocDescriptionSectionImpl(node: ASTNode): DlangDocPsiElementImpl(node) {
    fun getSections(): List<PsiElement> {
        return findChildrenByType(DDocElementTypes.DDOC_SECTION_PARAGRAPH)
    }
}
