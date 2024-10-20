package io.github.intellij.dlanguage.lexer

import com.intellij.lexer.LexerBase
import com.intellij.psi.StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN
import com.intellij.psi.tree.IElementType
import io.github.intellij.dlanguage.psi.DlangTypes.DELIMITED_STRING
import io.github.intellij.dlanguage.utils.getCorrespondingClosingDelimiter
import io.github.intellij.dlanguage.utils.getOpeningDelimiter

/**
 * Lex the string to highlight the Delimiter in the delimitedString
 */
class DDelimitedStringDelimiterLexer : LexerBase() {
    private var tokenStart: Int = 0
    private var tokenEnd: Int = 0
    private lateinit var bufferSequence: CharSequence
    private var bufferEnd: Int = 0
    private var state: Int = 0
    private var openingDelimiter: String? = null

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        this.bufferSequence = buffer
        bufferEnd = endOffset
        state = initialState

        tokenEnd = startOffset

        advance()
    }

    override fun getState(): Int = state

    override fun getTokenType(): IElementType? {
        // We’re at the end of the string token
        if (tokenStart >= tokenEnd) {
            return null
        }

        if (state == 2 || state == 4) {
            return VALID_STRING_ESCAPE_TOKEN
        }

        return DELIMITED_STRING
    }

    override fun getTokenStart(): Int = tokenStart

    override fun getTokenEnd(): Int = tokenEnd

    override fun advance() {
        tokenStart = tokenEnd
        if (tokenStart >= bufferEnd) {
            state = 0
            return
        }
        when (state) {
            0 -> { // beginning q"
                tokenEnd += 2
                assert(bufferSequence.subSequence(tokenStart, tokenEnd).toString() == "q\"")
                state = 1
            }
            1 -> { // delimiter
                tokenEnd = locateStartingToken(tokenStart)
            }
            2 -> { // token end
                tokenEnd = locateEndingToken(tokenStart)
            }
            3 -> { // closing delimiter
                tokenEnd = tokenStart + getCorrespondingClosingDelimiter(openingDelimiter!!).length
                state = 4
            }
            else -> { // end of string
                tokenEnd = bufferEnd
                state = 0
            }
        }
    }

    override fun getBufferSequence(): CharSequence = bufferSequence

    override fun getBufferEnd(): Int = bufferEnd

    private fun locateStartingToken(start: Int): Int {
        openingDelimiter = getOpeningDelimiter(bufferSequence.substring(start, bufferEnd - 1))
        if (openingDelimiter == null) {
            state = 0
            return bufferEnd
        }
        state = 2
        return start + openingDelimiter!!.length
    }

    private fun locateEndingToken(start: Int): Int {
        val delimiter = getCorrespondingClosingDelimiter(openingDelimiter!!)
        state = 3
        return start + bufferSequence.subSequence(start, bufferEnd).split(delimiter)[0].length
    }
}
