package net.masterthought.dlanguage;

import com.intellij.lang.Language;

public class DLanguage extends Language {

    public static final DLanguage INSTANCE = new DLanguage();

    private DLanguage(){
        super("D");
    }

}
