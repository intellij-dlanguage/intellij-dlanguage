package io.github.intellij.dlanguage.lexer

import com.intellij.lexer.LexerBase
import com.intellij.openapi.util.text.StringUtil.isHexDigit
import com.intellij.openapi.util.text.StringUtil.isOctalDigit
import com.intellij.psi.StringEscapesTokenTypes.*
import com.intellij.psi.tree.IElementType
import com.intellij.util.text.CharArrayUtil.indexOf
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.psi.DlangTypes.VALID_NAMED_CHARACTER_ENTITY
import java.lang.Integer.min

/**
 * Lex a token to generate check if all his content is valid, this is used for highlighting to show errors in strings.
 */
class DLiteralEscapesLexer(private val defaultToken: IElementType) : LexerBase() {
    private var tokenStart: Int = 0
    private var tokenEnd: Int = 0
    private lateinit var bufferSequence: CharSequence
    private var bufferEnd: Int = 0
    private var state: Int = 0

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

        // skip non-escapes sequences
        if (bufferSequence[tokenStart] != '\\') {
            return defaultToken
        }

        if (tokenStart + 1 >= tokenEnd) {
            return INVALID_CHARACTER_ESCAPE_TOKEN
        }

        return when (bufferSequence[tokenStart + 1]) {
            'u', 'U' ->
                when {
                    isValidUnicodeEscape(tokenStart, tokenEnd) -> VALID_STRING_ESCAPE_TOKEN
                    else -> INVALID_UNICODE_ESCAPE_TOKEN
                }
            'x' ->
                when {
                    isValidByteEscape(tokenStart, tokenEnd) -> VALID_STRING_ESCAPE_TOKEN
                    else -> INVALID_CHARACTER_ESCAPE_TOKEN
                }
            '\'', '"', '?', '\\', '0', 'a', 'b', 'f', 'n', 'r', 't', 'v' -> VALID_STRING_ESCAPE_TOKEN
            '&' -> when {
                isValidNamedCharacterEntities(tokenStart, tokenEnd) -> VALID_NAMED_CHARACTER_ENTITY
                else -> INVALID_CHARACTER_ESCAPE_TOKEN
            }
            '$' -> if (defaultToken == DlangTypes.IES_TEXT) VALID_STRING_ESCAPE_TOKEN else INVALID_CHARACTER_ESCAPE_TOKEN
            else ->
                when {
                    isValidOctalDigit(tokenStart, tokenEnd) -> VALID_STRING_ESCAPE_TOKEN
                    isValidNamedCharacterEntities(tokenStart, tokenEnd) -> VALID_NAMED_CHARACTER_ENTITY
                    else -> INVALID_CHARACTER_ESCAPE_TOKEN
                }
        }
    }

    override fun getTokenStart(): Int = tokenStart

    override fun getTokenEnd(): Int = tokenEnd

    override fun advance() {
        tokenStart = tokenEnd
        tokenEnd = locateToken(tokenStart)
    }

    override fun getBufferSequence(): CharSequence = bufferSequence

    override fun getBufferEnd(): Int = bufferEnd

    /**
     * Find next token location.
     * @return end offset of the next token. If offset is equals to start, then it’s the end of the file
     */
    private fun locateToken(start: Int): Int {
        if (start >= bufferEnd) {
            return start
        }

        // escape sequence
        if (bufferSequence[start] == '\\') {
            val i = start + 1

            if (i >= bufferEnd) {
                return bufferEnd
            }

            var spaceIdx = indexOf(bufferSequence, " ", i, bufferEnd)
            var tabIdx = indexOf(bufferSequence, "\t", i, bufferEnd)
            spaceIdx = if (spaceIdx == -1) bufferEnd else spaceIdx
            tabIdx = if (tabIdx == -1) bufferEnd else tabIdx
            val endIdx = min(tabIdx, spaceIdx)
            when (bufferSequence[i]) {
                'u', 'U' -> {
                    val startIdx = if (bufferSequence[i] == 'u') 4 else 8
                    return min(i + startIdx + 1, endIdx)
                }
                'x' -> {
                    return min(i + 2 + 1, endIdx)
                }
                '&' -> {
                    var semicolonIdx = indexOf(bufferSequence, ";", i, bufferEnd)
                    semicolonIdx = if (semicolonIdx == -1) bufferEnd else semicolonIdx
                    return min(semicolonIdx + 1, endIdx)
                }
            }
            // is octal?
            if (isOctalDigit(bufferSequence[i])) {
                return if (i + 1 < bufferEnd && isOctalDigit(bufferSequence[i + 1])) {
                    return if (i + 2 < bufferEnd && isOctalDigit(bufferSequence[i + 2])) {
                        i + 2 + 1
                    } else {
                        i + 1 + 1
                    }
                } else {
                    i + 1
                }
            }
            return i + 1
        }
        // else find next escape sequence if any
        val idx = indexOf(bufferSequence, "\\", start + 1, bufferEnd)
        return if (idx != -1) idx else bufferEnd
    }

    private fun isValidUnicodeEscape(start: Int, end: Int): Boolean {
        val expectedDigitCount = if (bufferSequence[start + 1] == 'U') 8 else 4
        return end - start == expectedDigitCount + 2 &&
            bufferSequence.subSequence(start + 2, end).all { isHexDigit(it) }
    }

    private fun isValidByteEscape(start: Int, end: Int): Boolean =
        end - start == 4 &&
            bufferSequence.subSequence(start + 2, end).all { isHexDigit(it) }

    private fun isValidOctalDigit(start: Int, end: Int): Boolean =
        end - start <= 4 &&
            bufferSequence.subSequence(start + 1, end).all { isOctalDigit(it) }

    private fun isValidNamedCharacterEntities(start: Int, end: Int): Boolean =
        bufferSequence[end-1] == ';'
}
