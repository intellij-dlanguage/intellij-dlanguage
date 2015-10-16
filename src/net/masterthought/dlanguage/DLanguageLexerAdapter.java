package net.masterthought.dlanguage;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class DLanguageLexerAdapter extends FlexAdapter {

    public DLanguageLexerAdapter() {
        super(new _DLanguageLexer((Reader) null));
    }
}
