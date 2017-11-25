package io.github.intellij.dlanguage.highlighting

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import io.github.intellij.dlanguage.colors.DColor
import io.github.intellij.dlanguage.highlighting.DHighlightingTokenSets as TokenSets

/**
 * Highlighter for D source. Uses DHighlightingLexer to create tokens of
 * DElementType, then returns the highlighting TextAttributesKey associated
 * with the DElementType token.
 */
class DHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = FlexAdapter(DHighlightingLexer())

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> =
        pack(tokenSetMap(tokenType)?.textAttributesKey)

    private fun tokenSetMap(tokenType: IElementType): DColor? = when (tokenType) {
        TokenType.BAD_CHARACTER -> DColor.ILLEGAL
        in TokenSets.LINE_DOC -> DColor.LINE_DOC
        in TokenSets.LINE_COMMENT -> DColor.LINE_COMMENT
        in TokenSets.BLOCK_COMMENT -> DColor.BLOCK_COMMENT
        in TokenSets.DOC_COMMENT -> DColor.DOC_COMMENT
        in TokenSets.AT_ATTRIBUTE -> DColor.AT_ATTRIBUTE
        in TokenSets.STRING -> DColor.STRING
        in TokenSets.CHAR -> DColor.CHAR
        in TokenSets.NUMBER -> DColor.NUMBER
        in TokenSets.OPERATOR -> DColor.OPERATOR
        in TokenSets.PARENTHESES_RIGHT -> DColor.PARENTHESES
        in TokenSets.PARENTHESES_LEFT -> DColor.PARENTHESES
        in TokenSets.BRACES_RTGHT -> DColor.BRACES
        in TokenSets.BRACES_LEFT -> DColor.BRACES
        in TokenSets.SEMICOLON -> DColor.SEMICOLON
        in TokenSets.COMMA -> DColor.COMMA
        in TokenSets.DOT -> DColor.DOT
        in TokenSets.BRACKETS_RIGHT -> DColor.BRACKETS
        in TokenSets.BRACKETS_LEFT -> DColor.BRACKETS
        in TokenSets.MODULE_DEFINITION -> DColor.MODULE_DEFINITION
        in TokenSets.FUNCTION_DEFINITION -> DColor.FUNCTION_DEFINITION
        in TokenSets.KEYWORD -> DColor.KEYWORD
        else -> null
    }
}
