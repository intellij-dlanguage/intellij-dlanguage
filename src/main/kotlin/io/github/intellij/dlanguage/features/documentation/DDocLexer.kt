package io.github.intellij.dlanguage.features.documentation

import com.intellij.lexer.LexerBase
import com.intellij.lexer.MergingLexerAdapter
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import com.intellij.util.text.CharArrayUtil
import io.github.intellij.dlanguage.documentation.psi.DDocMetaElementTypes.DDOC_COMMENT_LEADING_ASTERISKS
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_COMMENT_DATA
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_EMBEDDED_CODE_CONTENT
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_MACRO_ARGUMENT
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_WHITESPACE
import java.io.IOException

val ddocTokenMerge = TokenSet.create(DDOC_COMMENT_DATA, DDOC_MACRO_ARGUMENT, DDOC_WHITESPACE, DDOC_EMBEDDED_CODE_CONTENT)
class DDocLexer : MergingLexerAdapter(AsteriskStripperLexer(_DDocLexer(null)), ddocTokenMerge)

private class AsteriskStripperLexer(val myFlex: _DDocLexer) : LexerBase() {

    lateinit var myBuffer: CharSequence
    var myBufferIndex: Int = 0
    var myBufferEndOffset: Int = 0
    var myTokenEndOffset: Int = 0
    var myState: Int = 0
    var myTokenType: IElementType? = null
    var myLeadingAsteriskChar: Char? = null

    var isAfterLineBreak: Boolean = false
    var isInLeadingSpace: Boolean = false
    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        myBuffer = buffer
        myBufferIndex = startOffset
        myBufferEndOffset = endOffset
        myTokenType = null
        myTokenEndOffset = startOffset
        myLeadingAsteriskChar = buffer[startOffset + 1]
        myFlex.reset(myBuffer, startOffset, endOffset, initialState)
    }

    override fun getState(): Int = myState

    override fun getBufferSequence(): CharSequence = myBuffer

    override fun getBufferEnd(): Int = myBufferEndOffset

    override fun getTokenType(): IElementType? {
        locateToken()
        return myTokenType
    }

    override fun getTokenStart(): Int {
        locateToken()
        return myBufferIndex
    }

    override fun getTokenEnd(): Int {
        locateToken()
        return myTokenEndOffset
    }

    override fun advance() {
        locateToken()
        myTokenType = null
    }

    private fun locateToken() {
        if (myTokenType != null) return
        doLocateToken()

        if (myTokenType == DDOC_WHITESPACE)
            isAfterLineBreak = CharArrayUtil.containLineBreaks(myBuffer, tokenStart, tokenEnd)
    }

    private fun doLocateToken() {
        if (myTokenEndOffset == myBufferEndOffset) {
            myTokenType = null
            myBufferIndex = myBufferEndOffset
            return
        }

        myBufferIndex = myTokenEndOffset

        if (isAfterLineBreak) {
            isAfterLineBreak = false
            isInLeadingSpace = true

            // skip leading star (or +)
            if (myTokenEndOffset < myBufferEndOffset && myBuffer[myTokenEndOffset] == myLeadingAsteriskChar &&
                (myTokenEndOffset + 1 >= myBufferEndOffset || myBuffer[myTokenEndOffset + 1] != '/')) {
                myTokenEndOffset++
            }

            if (myBufferIndex < myTokenEndOffset) {
                myTokenType = DDOC_COMMENT_LEADING_ASTERISKS
                return
            }
        }

        if (isInLeadingSpace) {
            isInLeadingSpace = false
            if (myFlex.yystate() == _DDocLexer.EMBEDDED_CODE) {
                myFlex.yybegin(_DDocLexer.EMBEDDED_CODE_START)
            }
            // Don’t change state if we are in macro
            else if (myFlex.yystate() != _DDocLexer.DOC_MACRO && myFlex.yystate() != _DDocLexer.DOC_MACRO_START) {
                myFlex.yybegin(_DDocLexer.COMMENT_DATA_START)
            }
        }

        flexLocateToken()
    }

    private fun flexLocateToken() {
        try {
            myState = myFlex.yystate()
            myFlex.goTo(myBufferIndex)
            myTokenType = myFlex.advance()
            myTokenEndOffset = myFlex.tokenEnd
        } catch (e: IOException) {
            // Can’t be
        }
    }

}
