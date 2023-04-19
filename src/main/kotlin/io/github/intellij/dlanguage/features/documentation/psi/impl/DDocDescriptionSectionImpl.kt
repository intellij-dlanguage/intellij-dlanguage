package io.github.intellij.dlanguage.features.documentation.psi.impl

import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes

class DDocDescriptionSectionImpl(node: ASTNode): DlangDocPsiElementImpl(node) {
    fun getSections(): List<DDocAnonymousSectionImpl> {
        return findChildrenByType(DDocElementTypes.DDOC_ANONYMOUS_SECTION)
    }
}
