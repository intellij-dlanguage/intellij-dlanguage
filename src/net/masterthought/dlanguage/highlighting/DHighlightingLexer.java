package net.masterthought.dlanguage.highlighting;

import com.intellij.lexer.FlexAdapter;
import net.masterthought.dlanguage.DLanguageHighlightingLexer;
import net.masterthought.dlanguage.lexer.DLanguageLexer;

public class DHighlightingLexer extends FlexAdapter {
    public DHighlightingLexer() {
        super(new DLanguageHighlightingLexer(null));
    }
}
