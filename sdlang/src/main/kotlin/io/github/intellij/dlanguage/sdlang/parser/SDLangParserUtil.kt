package io.github.intellij.dlanguage.sdlang.parser

import com.intellij.lang.PsiBuilder
import com.intellij.lang.parser.GeneratedParserUtilBase
import com.intellij.psi.TokenType


object SDLangParserUtil : GeneratedParserUtilBase() {

    @JvmStatic
    fun atNewLine(b: PsiBuilder, level: Int, parser: Parser): Boolean {
        val marker = enter_section_(b)
        b.eof() // skip whitespace
        val result = isNextAfterNewLine(b) && parser.parse(b, level)
        exit_section_(b, marker, null, result)
        return result
    }
}

private fun isNextAfterNewLine(b: PsiBuilder): Boolean {
    val prevToken = b.rawLookup(-1)
    return prevToken == null || prevToken == TokenType.WHITE_SPACE && b.rawLookupText(-1).contains("\n")
}

/** Similar to [com.intellij.lang.PsiBuilderUtil.rawTokenText] */
private fun PsiBuilder.rawLookupText(steps: Int): CharSequence {
    val start = rawTokenTypeStart(steps)
    val end = rawTokenTypeStart(steps + 1)
    return if (start == -1 || end == -1) "" else originalText.subSequence(start, end)
}
