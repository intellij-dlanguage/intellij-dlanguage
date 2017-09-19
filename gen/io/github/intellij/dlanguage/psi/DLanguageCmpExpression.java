package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageCmpExpression extends PsiElement {
    @Nullable
    DLanguageShiftExpression getShiftExpression();

    @Nullable
    DLanguageEqualExpression getEqualExpression();

    @Nullable
    DLanguageIdentityExpression getIdentityExpression();

    @Nullable
    DLanguageRelExpression getRelExpression();

    @Nullable
    DLanguageInExpression getInExpression();
}
