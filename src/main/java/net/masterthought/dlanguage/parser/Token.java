package net.masterthought.dlanguage.parser;

import com.intellij.psi.tree.IElementType;

/**
 * Created by francis on 6/28/2017.
 * a class that emulates the behavior of tokens in libdparse
 */
public class Token {
    public final String text;//todo check that this is correct

    public Token(IdType type) {
        text = type.type.toString();
        this.type = type;
    }

    public final IdType type;

    static class IdType{
        IElementType type;

        public IdType(IElementType matchingType) {
            type = matchingType;
        }

        @Override
        public String toString() {
            return type.toString();
        }
    }
}
