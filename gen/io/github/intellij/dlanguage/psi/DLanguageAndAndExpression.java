package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAndAndExpression extends PsiElement {

    @Nullable
    public DLanguageAndAndExpression getAndAndExpression();

    @Nullable
    public DLanguageOrExpression getOrExpression();

    @Nullable
    public PsiElement getOP_BOOL_AND();

}
