package io.github.intellij.dlanguage.features

import com.intellij.lang.CodeDocumentationAwareCommenter
import com.intellij.psi.PsiComment
import com.intellij.psi.tree.IElementType
import io.github.intellij.dlanguage.psi.DlangTypes

class DCommenter : CodeDocumentationAwareCommenter {
    override fun getLineCommentPrefix(): String {
        return "//"
    }

    override fun getBlockCommentPrefix(): String {
        return "/*"
    }

    override fun getBlockCommentSuffix(): String {
        return "*/"
    }

    override fun getCommentedBlockCommentPrefix(): String? {
        return null
    }

    override fun getCommentedBlockCommentSuffix(): String? {
        return null
    }

    override fun getLineCommentTokenType(): IElementType? {
        return DlangTypes.LINE_COMMENT
    }

    override fun getBlockCommentTokenType(): IElementType? {
        return DlangTypes.BLOCK_COMMENT
    }

    override fun getDocumentationCommentPrefix(): String {
        return "/**"
    }

    override fun getDocumentationCommentLinePrefix(): String {
        return "*"
    }

    override fun getDocumentationCommentSuffix(): String {
        return "*/"
    }

    override fun isDocumentationComment(element: PsiComment): Boolean {
        return false
//        return element.getTokenType() == DElementTypes.DOCCOMMENT_LINE ||
//                element.getTokenType() == DElementTypes.DOCCOMMENT_MULTI ||
//                element.getTokenType() == DElementTypes.DOCCOMMENT_NESTED;
    }

    override fun getDocumentationCommentTokenType(): IElementType? {
        return DlangTypes.LINE_COMMENT
    }
}
