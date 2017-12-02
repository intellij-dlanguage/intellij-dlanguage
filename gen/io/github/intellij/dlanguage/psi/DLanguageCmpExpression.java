package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageCmpExpression extends PsiElement {

    @Nullable
    public DLanguageShiftExpression getShiftExpression();

    @Nullable
    public DLanguageEqualExpression getEqualExpression();

    @Nullable
    public DLanguageIdentityExpression getIdentityExpression();

    @Nullable
    public DLanguageRelExpression getRelExpression();

    @Nullable
    public DLanguageInExpression getInExpression();
}
