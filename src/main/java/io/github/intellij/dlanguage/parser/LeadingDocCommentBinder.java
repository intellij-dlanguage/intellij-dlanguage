package io.github.intellij.dlanguage.parser;


import com.intellij.lang.WhitespacesAndCommentsBinder;
import com.intellij.psi.tree.IElementType;
import io.github.intellij.dlanguage.psi.DTokenSets;

import java.util.List;

public class LeadingDocCommentBinder implements WhitespacesAndCommentsBinder {

    static final LeadingDocCommentBinder INSTANCE = new LeadingDocCommentBinder();

    @Override
    public int getEdgePosition(List<? extends IElementType> tokens, boolean atStreamEdge, TokenTextGetter getter) {
        // bind all doc comments before a declaration
        for (int idx = 0; idx < tokens.size(); idx++) {
            if (DTokenSets.DOCS.contains(tokens.get(idx))) {
                return idx;
            }
        }
        return tokens.size();
    }
}
