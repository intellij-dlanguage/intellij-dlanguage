package io.github.intellij.dlanguage.highlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.StringEscapesTokenTypes
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import io.github.intellij.dlanguage.colors.DColor
import io.github.intellij.dlanguage.lexer.DHighlightingLexer
import io.github.intellij.dlanguage.highlighting.DHighlightingTokenSets as TokenSets

/**
 * Highlighter for D source. Uses DHighlightingLexer to create tokens of
 * DElementType, then returns the highlighting TextAttributesKey associated
 * with the DElementType token.
 */
class DHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer = DHighlightingLexer()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> = pack(tokenSetMap(tokenType)?.textAttributesKey)

    private fun tokenSetMap(tokenType: IElementType): DColor? = when (tokenType) {
        TokenType.BAD_CHARACTER -> DColor.ILLEGAL
        in TokenSets.LINE_COMMENT -> DColor.LINE_COMMENT
        in TokenSets.LINE_DOCUMENTATION -> DColor.LINE_DOCUMENTATION
        in TokenSets.BLOCK_COMMENT -> DColor.BLOCK_COMMENT
        in TokenSets.BLOCK_DOCUMENTATION -> DColor.BLOCK_DOCUMENTATION
        in TokenSets.AT_ATTRIBUTE -> DColor.AT_ATTRIBUTE
        in TokenSets.STRING_LITERAL -> DColor.STRING_LITERAL
        in TokenSets.CHAR -> DColor.CHAR
        in TokenSets.NUMBER -> DColor.NUMBER
        in TokenSets.OPERATOR -> DColor.OPERATOR
        in TokenSets.PARENTHESES -> DColor.PARENTHESES
        in TokenSets.BRACES -> DColor.BRACES
        in TokenSets.BRACKETS -> DColor.BRACKETS
        in TokenSets.SEMICOLON -> DColor.SEMICOLON
        in TokenSets.COMMA -> DColor.COMMA
        in TokenSets.DOT -> DColor.DOT
        in TokenSets.MODULE_DEFINITION -> DColor.MODULE_DEFINITION
        in TokenSets.KEYWORD -> DColor.KEYWORD
        StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN -> DColor.VALID_STRING_ESCAPE
        StringEscapesTokenTypes.INVALID_CHARACTER_ESCAPE_TOKEN -> DColor.INVALID_STRING_ESCAPE
        StringEscapesTokenTypes.INVALID_UNICODE_ESCAPE_TOKEN -> DColor.INVALID_STRING_ESCAPE
        else -> null
    }
}
