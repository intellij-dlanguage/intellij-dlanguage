package io.github.intellij.dlanguage.lexer

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.LayeredLexer
import io.github.intellij.dlanguage.DHighlightingLexer
import io.github.intellij.dlanguage.psi.DlangTypes.*

class DHighlightingLexer : LayeredLexer(FlexAdapter(DHighlightingLexer())) {
    init {
        registerLayer(DLiteralEscapesLexer(CHARACTER_LITERAL), CHARACTER_LITERAL)
        // TODO should be DOUBLE_DELIMITED_STRING only but the lexer need to return different
        //      token for different string types (like the psi lexer)
        registerLayer(DLiteralEscapesLexer(STRING), STRING)

    }
}
