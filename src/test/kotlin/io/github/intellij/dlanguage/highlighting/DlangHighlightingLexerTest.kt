package io.github.intellij.dlanguage.highlighting

class DlangHighlightingLexerTest : DHighlightingLexerTestBase("highlighting") {
    // Ensure that the parser doesn’t crash if a comment is unclosed
    fun testcomment_unclosed() {
        doTest("/* unclosed comment", "DlangTokenType.BLOCK_COMMENT ('/* unclosed comment')", createLexer())
    }

    // strings
    fun testchar() = doTest()
    fun teststrings_escapes() = doTest()
}
