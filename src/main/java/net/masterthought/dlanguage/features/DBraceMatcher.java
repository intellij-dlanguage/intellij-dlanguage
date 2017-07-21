package net.masterthought.dlanguage.features;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import net.masterthought.dlanguage.psi.DLanguageTypes;
import net.masterthought.dlanguage.psi.DTokenSets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DBraceMatcher implements PairedBraceMatcher {
    private static final BracePair[] PAIRS = new BracePair[]{
            new BracePair(DLanguageTypes.PARENTHESES_LEFT, DLanguageTypes.PARENTHESES_RIGHT, false),
            new BracePair(DLanguageTypes.BRACES_LEFT, DLanguageTypes.BRACES_RIGHT, true),
            new BracePair(DLanguageTypes.BRACKETS_LEFT, DLanguageTypes.BRACKETS_RIGHT, false),
    };

    private static final TokenSet alwaysMatch = TokenSet.create(DLanguageTypes.OP_BRACES_LEFT);

    @Override
    public BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull final IElementType lbraceType,
                                                   @Nullable final IElementType contextType) {
        return alwaysMatch.contains(lbraceType)
                || DTokenSets.WHITESPACES.contains(contextType)
                || DTokenSets.LINE_COMMENTS.contains(contextType)
                || DTokenSets.BLOCK_COMMENTS.contains(contextType)
                || DLanguageTypes.OP_PAR_RIGHT == contextType
                || null == contextType;
    }

    @Override
    public int getCodeConstructStart(final PsiFile file, final int openingBraceOffset) {
        return openingBraceOffset;
    }
}

