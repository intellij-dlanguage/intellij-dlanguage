package net.masterthought.dlanguage.features;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import net.masterthought.dlanguage.psi.DTokenSets;
import net.masterthought.dlanguage.psi.interfaces.DElementTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DBraceMatcher implements PairedBraceMatcher {
    private static final BracePair[] PAIRS = new BracePair[]{
            new BracePair(DElementTypes.OPEN_PARENS, DElementTypes.OPEN_PARENS, false),
            new BracePair(DElementTypes.OPEN_BRACE, DElementTypes.CLOSE_BRACE, true),
            new BracePair(DElementTypes.OPEN_BRACKET, DElementTypes.CLOSE_BRACKET, false),
    };

    private static final TokenSet alwaysMatch = TokenSet.create(DElementTypes.OPEN_BRACE);

    @Override
    public BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return alwaysMatch.contains(lbraceType)
                || DTokenSets.WHITESPACES.contains(contextType)
                || DTokenSets.COMMENTS.contains(contextType)
                || DElementTypes.CLOSE_PARENS == contextType
                || null == contextType;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}

