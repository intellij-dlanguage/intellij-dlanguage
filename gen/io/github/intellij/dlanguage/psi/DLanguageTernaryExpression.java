package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTernaryExpression extends PsiElement {

    @Nullable
    public PsiElement getOP_QUEST();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public DLanguageOrOrExpression getOrOrExpression();

    @Nullable
    public DLanguageExpression getExpression();

    @Nullable
    public DLanguageTernaryExpression getTernaryExpression();
}
