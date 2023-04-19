package io.github.intellij.dlanguage.features.documentation.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_SECTION_CONTENT
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_SECTION_TITLE

open class DDocNamedSectionImpl(node: ASTNode): DlangDocPsiElementImpl(node) {

    fun getTitle(): PsiElement? {
        return findChildByType(DDOC_SECTION_TITLE)
    }

    fun getContent(): PsiElement? {
        return findChildByType(DDOC_SECTION_CONTENT)
    }

}
