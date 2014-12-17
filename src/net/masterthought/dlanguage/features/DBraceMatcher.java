package net.masterthought.dlanguage.features;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import net.masterthought.dlanguage.psi.DLanguageTokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DBraceMatcher implements PairedBraceMatcher {
    private static final BracePair[] PAIRS = new BracePair[]{
            new BracePair(DLanguageTokenType.OPEN_PARENS, DLanguageTokenType.OPEN_PARENS, false),
            new BracePair(DLanguageTokenType.OPEN_BRACE, DLanguageTokenType.CLOSE_BRACE, true),
            new BracePair(DLanguageTokenType.OPEN_BRACKET, DLanguageTokenType.CLOSE_BRACKET, false),
    };

    private static final TokenSet alwaysMatch = TokenSet.create(DLanguageTokenType.OPEN_BRACE);

    @Override
    public BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return alwaysMatch.contains(lbraceType)
                || DLanguageTokenType.WHITESPACES.contains(contextType)
                || DLanguageTokenType.COMMENTS.contains(contextType)
                || DLanguageTokenType.CLOSE_PARENS == contextType
                || null == contextType;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}

