package io.github.intellij.dlanguage.features.documentation

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.ILazyParseableElementType
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.features.documentation.psi.DlangDocTypes
import io.github.intellij.dlanguage.features.documentation.psi.impl.DlangDocCommentImpl

class DlangDocCommentType(debugName: String) : ILazyParseableElementType(debugName, DLanguage) {

    override fun doParseContents(chameleon: ASTNode, psi: PsiElement): ASTNode {
        // TODO really parse comment and construct real tree
        //      elements should have extra */+ removed
        val root = DlangDocCommentImpl(this, null)
        root.rawAddChildrenWithoutNotifications(LeafPsiElement(DlangDocTypes.docDataType, chameleon.chars))
        return root.firstChildNode
    }

    override fun createNode(text: CharSequence?): ASTNode = DlangDocCommentImpl(this, text)

    override fun toString(): String = "DlangTokenType." + super.toString()
}
