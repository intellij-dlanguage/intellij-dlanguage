package io.github.intellij.dlanguage.parser;

import com.intellij.lang.WhitespacesAndCommentsBinder;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import io.github.intellij.dlanguage.psi.DTokenSets;

import java.util.List;

public class TrailingDocCommentBinder implements WhitespacesAndCommentsBinder {

    public static final TrailingDocCommentBinder INSTANCE = new TrailingDocCommentBinder();

    @Override
    public int getEdgePosition(List<? extends IElementType> tokens, boolean atStreamEdge, TokenTextGetter getter) {
        // bind trailing doc comments to their declaration only if they are on the same line
        int candidate = 0;
        while (candidate < tokens.size()) {
            if (DTokenSets.WHITESPACES.contains(tokens.get(candidate)) && StringUtil.containsLineBreak(getter.get(candidate))) {
                break;
            }
            candidate++;
        }
        return candidate;
    }
}
