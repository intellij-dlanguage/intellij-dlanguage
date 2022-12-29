package io.github.intellij.dlanguage.sdlang.highlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import io.github.intellij.dlanguage.sdlang.colors.SDLangColor
import io.github.intellij.dlanguage.sdlang.lexer.SDLangHighlightingLexer
import io.github.intellij.dlanguage.sdlang.psi.SDLANG_STRINGS
import io.github.intellij.dlanguage.sdlang.psi.SDLangElementTypes

class SDLangHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer = SDLangHighlightingLexer()

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> =
        pack(tokenSetMap(tokenType)?.textAttributesKey)

    private fun tokenSetMap(tokenType: IElementType?): SDLangColor? = when (tokenType) {
        TokenType.BAD_CHARACTER -> SDLangColor.ILLEGAL
        SDLangElementTypes.LINE_COMMENT -> SDLangColor.LINE_COMMENT
        SDLangElementTypes.BLOCK_COMMENT -> SDLangColor.BLOCK_COMMENT
        in SDLANG_STRINGS -> SDLangColor.STRING
        SDLangElementTypes.TRUE, SDLangElementTypes.FALSE, SDLangElementTypes.NULL -> SDLangColor.KEYWORD
        SDLangElementTypes.INTEGER, SDLangElementTypes.LONG_INT, SDLangElementTypes.FLOAT, SDLangElementTypes.DOUBLE, SDLangElementTypes.DECIMAL -> SDLangColor.NUMBER
        SDLangElementTypes.DATE, SDLangElementTypes.TIME, SDLangElementTypes.DATE_TIME -> SDLangColor.DATETIME
        SDLangElementTypes.SEMICOLON -> SDLangColor.SEMICOLON
        SDLangElementTypes.L_CURLY, SDLangElementTypes.R_CURLY -> SDLangColor.BRACES
        else -> null
    }
}
