package net.masterthought.dlanguage.lexer;

import com.intellij.lexer.LayeredLexer;

/**
 *
 */
public class DHighlightingLexer extends LayeredLexer {
    public DHighlightingLexer() {
        super(new DLexer());
    }
}
