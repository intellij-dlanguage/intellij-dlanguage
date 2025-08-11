package io.github.intellij.dlanguage.lexer

import com.intellij.lexer.LexerBase
import com.intellij.psi.tree.IElementType
import io.github.intellij.dlanguage.psi.DlangTypes

class DlangLexer : LexerBase() {

    private val flexLexer = _DlangLexer()
    private lateinit var myBuffer: CharSequence
    private var myTokenType: IElementType? = null

    private var myTokenStart = 0
    private var myTokenEnd = 0

    private var myBufferEnd = 0
    private var myState = 0

    private var myTokenStringIndexStart = -1
    private var myTokenStringBraceDepth = 0

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        myBuffer = buffer
        myTokenEnd = startOffset
        myTokenStart = startOffset
        myBufferEnd = endOffset
        myTokenType = null
        flexLexer.reset(myBuffer, startOffset, endOffset, initialState)
    }

    override fun getState(): Int {
        locateToken()
        return myState
    }

    override fun getTokenType(): IElementType? {
        locateToken()
        return myTokenType
    }

    override fun getTokenStart(): Int {
        locateToken()
        return myTokenStart
    }

    override fun getTokenEnd(): Int {
        locateToken()
        return myTokenEnd
    }

    override fun advance() {
        locateToken()
        myTokenType = null
    }

    override fun getBufferSequence(): CharSequence = myBuffer

    override fun getBufferEnd(): Int = myBufferEnd

    private fun locateToken() {
        if (myTokenType != null) return

        if (myTokenEnd == myBufferEnd) {
            myTokenStart = myBufferEnd
            return
        }

        myTokenStart = myTokenEnd

        if (flexLexer.yystate() != 0) {
            flexLocateToken()
        } else if (myBuffer[myTokenStart] == 'q' && (myTokenStart + 1) < myBufferEnd && myBuffer[myTokenStart+1] == '{') {
            myTokenStringIndexStart = myTokenStart
            myTokenStringBraceDepth = 1
            myTokenEnd = myTokenStart + 2
            locateTokenStringEnd()
        } else {
            flexLocateToken()
        }

        if (myTokenEnd > myBufferEnd) {
            myTokenStart = myBufferEnd
        }
    }

    private fun locateTokenStringEnd() {
        while (myTokenEnd < myBufferEnd) {
            myTokenStart = myTokenEnd
            if (flexLexer.yystate() != 0) {
                flexLocateToken()
            } else if (myBuffer[myTokenStart] == '{') {
                myTokenEnd = myTokenStart + 1
                myTokenStringBraceDepth++
            } else if (myBuffer[myTokenStart] == '}') {
                myTokenEnd = myTokenStart + 1
                if (--myTokenStringBraceDepth == 0) {
                    break
                }
            } else {
                flexLocateToken()
            }
        }
        if (myBuffer[myTokenEnd] == 'c' || myBuffer[myTokenEnd] == 'w' || myBuffer[myTokenEnd] == 'd') {
            myTokenEnd += 1
        }
        myTokenStart = myTokenStringIndexStart
        myTokenType = DlangTypes.TOKEN_STRING
    }

    private fun flexLocateToken() {
        flexLexer.goTo(myTokenStart)
        myTokenType = flexLexer.advance()
        myTokenEnd = flexLexer.tokenEnd
    }
}
