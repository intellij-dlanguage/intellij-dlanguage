package io.github.intellij.dlanguage.parser;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 6/28/2017.
 * a class that emulates the behavior of tokens in libdparse
 */
public class Token {
    public final String text;//this is unused but could be used to make getTokenText lookaheads
    public final IdType type;

    public Token(@NotNull final IdType type) {
        text = type.type.toString();
        this.type = type;
    }

    public String toPrettyString() {
        if (type != null) {
            return type.toPrettyString();
        }
        return ("\"" + text + "\"").replace("DlangTokenType.", "").replace("ID", "an identifier");
    }

    static class IdType {
        final IElementType type;

        public IdType(@NotNull final IElementType matchingType) {
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
        public boolean equals(final Object obj) {
            if (!(obj instanceof IdType))
                return false;
            return type.getIndex() == ((IdType) obj).type.getIndex();
        }

        public String toPrettyString() {
            return type.toString().replace("DlangTokenType.", "").replace("ID", "an identifier");
        }
    }
}
