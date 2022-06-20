package io.github.intellij.dlanguage.features.documentation.psi.impl

import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.impl.source.tree.LazyParseablePsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.features.documentation.psi.DlangDocComment
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement

class DlangDocCommentImpl(type: IElementType, text: CharSequence?) : LazyParseablePsiElement(type, text), DlangDocComment {
    override fun getTokenType(): IElementType = elementType

    override fun getOwner(): DNamedElement? = PsiTreeUtil.getParentOfType(this, DNamedElement::class.java, /* strict */ true)

    override fun accept(visitor: PsiElementVisitor) = visitor.visitComment(this)
}
