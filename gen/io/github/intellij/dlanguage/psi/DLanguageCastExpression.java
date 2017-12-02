package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageCastExpression extends PsiElement {

    @Nullable
    public DLanguageCastQualifier getCastQualifier();

    @Nullable
    public DLanguageType getType();

    @Nullable
    public DLanguageUnaryExpression getUnaryExpression();

    @Nullable
    public PsiElement getKW_CAST();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

}
