package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageOrOrExpression extends PsiElement {
    @Nullable
    DLanguageAndAndExpression getAndAndExpression();

    @Nullable
    DLanguageOrOrExpression getOrOrExpression();

    @Nullable
    PsiElement getOP_BOOL_OR();

}
