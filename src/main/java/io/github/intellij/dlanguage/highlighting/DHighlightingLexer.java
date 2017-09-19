package io.github.intellij.dlanguage.highlighting;

import com.intellij.lexer.FlexAdapter;
import io.github.intellij.dlanguage.DlangHighlightingLexer;

public class DHighlightingLexer extends FlexAdapter {
    public DHighlightingLexer() {
        super(new DlangHighlightingLexer(null));
    }
}
