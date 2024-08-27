package io.github.intellij.dlanguage.parser

import com.intellij.lang.WhitespacesAndCommentsBinder
import com.intellij.psi.tree.IElementType
import io.github.intellij.dlanguage.psi.DTokenSets


class LeadingDocCommentBinder : WhitespacesAndCommentsBinder {
    override fun getEdgePosition(
        tokens: List<IElementType?>,
        atStreamEdge: Boolean,
        getter: WhitespacesAndCommentsBinder.TokenTextGetter
    ): Int {
        // bind all doc comments before a declaration
        for (idx in tokens.indices) {
            if (DTokenSets.DOCS.contains(tokens[idx])) {
                return idx
            }
        }
        return tokens.size
    }

    companion object {
        @JvmField
        val INSTANCE: LeadingDocCommentBinder = LeadingDocCommentBinder()
    }
}
