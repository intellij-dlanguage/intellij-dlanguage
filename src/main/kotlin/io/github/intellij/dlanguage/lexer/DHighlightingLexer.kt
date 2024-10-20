package io.github.intellij.dlanguage.lexer

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.LayeredLexer
import io.github.intellij.dlanguage.DlangLexer
import io.github.intellij.dlanguage.psi.DlangTypes.*

/**
 * Second step highlight pass to handle errors in tokens (string contents for example)
 */
class DHighlightingLexer : LayeredLexer(FlexAdapter(DlangLexer())) {
    init {
        registerLayer(DLiteralEscapesLexer(CHARACTER_LITERAL), CHARACTER_LITERAL)
        registerLayer(DLiteralEscapesLexer(DOUBLE_QUOTED_STRING), DOUBLE_QUOTED_STRING)
        registerLayer(DDelimitedStringDelimiterLexer(), DELIMITED_STRING)
    }
}
