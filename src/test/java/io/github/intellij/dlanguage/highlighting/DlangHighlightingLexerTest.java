package io.github.intellij.dlanguage.highlighting;

public class DlangHighlightingLexerTest extends DHighlightingLexerTestBase {

    public DlangHighlightingLexerTest() {
        super("highlighting");
    }

    // Ensure that the parser don’t crash if a comment is unclosed
    public void testcomment_unclosed() { doTest("/* unclosed comment", "DlangTokenType.BLOCK_COMMENT ('/* unclosed comment')", createLexer()); }

    // strings
    public void testchar() {
        doTest(true, true);
    }
    public void teststrings_escapes() {
        doTest(true, true);
    }
}
