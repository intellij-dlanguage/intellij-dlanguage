package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageOrOrExpression extends PsiElement {

    @Nullable
    public DLanguageAndAndExpression getAndAndExpression();

    @Nullable
    public DLanguageOrOrExpression getOrOrExpression();

    @Nullable
    public PsiElement getOP_BOOL_OR();

}
