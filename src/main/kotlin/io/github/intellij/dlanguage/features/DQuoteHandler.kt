package io.github.intellij.dlanguage.features

import com.intellij.codeInsight.editorActions.MultiCharQuoteHandler
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.highlighter.HighlighterIterator
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.StringEscapesTokenTypes
import io.github.intellij.dlanguage.psi.DlangTypes.*

class DQuoteHandler : SimpleTokenSetQuoteHandler(
    CHARACTER_LITERAL,
    DOUBLE_QUOTED_STRING,
    DELIMITED_STRING,
    WYSIWYG_STRING,
    ALTERNATE_WYSIWYG_STRING,
    TOKEN_STRING,
    HEX_STRING
), MultiCharQuoteHandler {

    override fun isOpeningQuote(iterator: HighlighterIterator, offset: Int): Boolean {
        val start = iterator.start
        return when (iterator.tokenType) {
            WYSIWYG_STRING, DELIMITED_STRING, TOKEN_STRING -> offset - start <= 2
            IES_START -> true
            else -> super.isOpeningQuote(iterator, offset)
        }
    }

    override fun isClosingQuote(iterator: HighlighterIterator, offset: Int): Boolean {
        var closingQuote = super.isClosingQuote(iterator, offset) || iterator.tokenType === IES_END
        if (closingQuote) {
            // check escape next
            if (!iterator.atEnd()) {
                iterator.advance()
                if (!iterator.atEnd() && StringEscapesTokenTypes.STRING_LITERAL_ESCAPES.contains(iterator.tokenType)) {
                    closingQuote = false
                }
                iterator.retreat()
            }
        }
        return closingQuote
    }

    override fun hasNonClosedLiteral(editor: Editor, iterator: HighlighterIterator, offset: Int): Boolean {
        if (!(myLiteralTokenSet.contains(iterator.tokenType) || iterator.tokenType === IES_START))
            return false
        val document = editor.document
        val text = document.text
        val quote = text.substring(iterator.start, iterator.start + 1)
        val hasCloseQuotes = StringUtil.contains(text.substring(offset + 1, iterator.end), quote)
        if (!hasCloseQuotes) {
            if (offset < 1) return true
            // Special case when " is inserted before existing ending quote but after escape character \
            return text[offset - 1] != '\\'
        }
        // check if parser interpreted next text block start quotes as end quotes for the current one
        val nTextQuotes = StringUtil.getOccurrenceCount(text.substring(iterator.end), quote)
        val nTextEscapeQuotes = if (quote == "\"") StringUtil.getOccurrenceCount(text.substring(iterator.end), "\\\"") else 0
        return (nTextQuotes - nTextEscapeQuotes)% 2 != 0
    }

    override fun getClosingQuote(iterator: HighlighterIterator, offset: Int): CharSequence? {
        return when (iterator.tokenType) {
            CHARACTER_LITERAL -> "'"
            DOUBLE_QUOTED_STRING,
            DELIMITED_STRING,
            WYSIWYG_STRING,
            IES_START,
            HEX_STRING -> "\""
            ALTERNATE_WYSIWYG_STRING -> "`"
            TOKEN_STRING -> "}"
            else -> null
        }
    }

    override fun isInsideLiteral(iterator: HighlighterIterator?): Boolean {
        return super.isInsideLiteral(iterator) || iterator?.tokenType === IES_TEXT
    }
}
