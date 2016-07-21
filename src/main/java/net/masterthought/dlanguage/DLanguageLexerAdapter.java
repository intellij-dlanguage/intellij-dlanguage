package net.masterthought.dlanguage;

import com.intellij.lexer.FlexAdapter;
import net.masterthought.dlanguage.lexer.DLanguageLexer;

import java.io.Reader;

public class DLanguageLexerAdapter extends FlexAdapter {

    public DLanguageLexerAdapter() {
        super(new DLanguageLexer((Reader) null));
    }
}
