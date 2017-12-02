package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTernaryExpression extends PsiElement {

    @Nullable
    PsiElement getOP_QUEST();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    DLanguageOrOrExpression getOrOrExpression();

    @Nullable
    DLanguageExpression getExpression();

    @Nullable
    DLanguageTernaryExpression getTernaryExpression();
}
