package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageCastExpression extends PsiElement {
    @Nullable
    DLanguageCastQualifier getCastQualifier();

    @Nullable
    DLanguageType getType();

    @Nullable
    DLanguageUnaryExpression getUnaryExpression();

    @Nullable
    PsiElement getKW_CAST();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

}
