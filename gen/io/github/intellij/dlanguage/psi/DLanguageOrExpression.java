package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageOrExpression extends PsiElement {

    @Nullable
    public DLanguageOrExpression getOrExpression();

    @Nullable
    public DLanguageXorExpression getXorExpression();

    @Nullable
    public PsiElement getOP_OR();

}
