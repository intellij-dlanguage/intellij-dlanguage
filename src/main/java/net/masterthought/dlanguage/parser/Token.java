package net.masterthought.dlanguage.parser;

import com.intellij.psi.tree.IElementType;

/**
 * Created by francis on 6/28/2017.
 * a class that emulates the behavior of tokens in libdparse
 */
public class Token {
    public final String text;//todo this is pretty much useless, needs remobving
    public final IdType type;

    public Token(IdType type) {
        text = type.type.toString();
        this.type = type;
    }

    static class IdType{
        final IElementType type;

        public IdType(IElementType matchingType) {
            type = matchingType;
        }

        @Override
        public String toString() {
            return type.toString();
        }

        @Override
        public int hashCode() {
            return type.getIndex();
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof IdType))
                return false;
            return type.getIndex() == ((IdType)obj).type.getIndex();
        }
    }
}
