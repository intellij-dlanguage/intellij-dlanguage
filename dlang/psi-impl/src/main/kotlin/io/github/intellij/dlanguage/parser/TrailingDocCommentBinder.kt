package io.github.intellij.dlanguage.parser

import com.intellij.lang.WhitespacesAndCommentsBinder
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.tree.IElementType
import io.github.intellij.dlanguage.psi.DTokenSets

class TrailingDocCommentBinder : WhitespacesAndCommentsBinder {
    override fun getEdgePosition(
        tokens: List<IElementType?>,
        atStreamEdge: Boolean,
        getter: WhitespacesAndCommentsBinder.TokenTextGetter
    ): Int {
        // bind trailing doc comments to their declaration only if they are on the same line
        var candidate = 0
        while (candidate < tokens.size) {
            if (DTokenSets.WHITESPACES.contains(tokens[candidate]) && StringUtil.containsLineBreak(
                    getter[candidate]
                )
            ) {
                break
            }
            candidate++
        }
        return candidate
    }

    companion object {
        @JvmField
        val INSTANCE: TrailingDocCommentBinder = TrailingDocCommentBinder()
    }
}
