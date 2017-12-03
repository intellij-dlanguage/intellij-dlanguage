package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAndAndExpression extends PsiElement {

    @Nullable
    DLanguageAndAndExpression getAndAndExpression();

    @Nullable
    DLanguageOrExpression getOrExpression();

    @Nullable
    PsiElement getOP_BOOL_AND();

}
