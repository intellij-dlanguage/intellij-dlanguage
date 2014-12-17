package net.masterthought.dlanguage.highlighting;

import com.intellij.lexer.LayeredLexer;
import net.masterthought.dlanguage.lexer.DLexer;

/**
 *
 */
public class DHighlightingLexer extends LayeredLexer {
    public DHighlightingLexer() {
        super(new DLexer());
    }
}
