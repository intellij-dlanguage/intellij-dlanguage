package io.github.intellij.dlanguage.documentation.psi.impl

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.impl.source.tree.LazyParseablePsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.PsiUtilCore
import io.github.intellij.dlanguage.documentation.psi.DDocMetaElementTypes.DDOC_COMMENT_END
import io.github.intellij.dlanguage.documentation.psi.DDocMetaElementTypes.DDOC_COMMENT_LEADING_ASTERISKS
import io.github.intellij.dlanguage.documentation.psi.DDocMetaElementTypes.DDOC_COMMENT_START
import io.github.intellij.dlanguage.documentation.psi.DlangDocComment
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement

class DlangDocCommentImpl(type: IElementType, text: CharSequence?) : LazyParseablePsiElement(type, text),
    DlangDocComment {
    override fun getTokenType(): IElementType = elementType

    // TODO introduce DPsiDocCommentOwner interface
    //  make some DPsiElement (the one that can have DDoc actually) implement it and return this new Interface instead
    override fun getOwner(): DNamedElement? = PsiTreeUtil.getParentOfType(this, DNamedElement::class.java, /* strict */ true)

    override fun accept(visitor: PsiElementVisitor) = visitor.visitComment(this)

    override fun sections(): Array<PsiElement> {
        val array = ArrayList<PsiElement>()
        var child = firstChildNode
        while (child != null) {
            val type = child.elementType
            if (type != DDOC_COMMENT_START && type != DDOC_COMMENT_END && type != DDOC_COMMENT_LEADING_ASTERISKS)
                array.add(child.psi)
            child = child.treeNext
        }
        return PsiUtilCore.toPsiElementArray(array)
    }
}
