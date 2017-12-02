package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageXorExpression extends PsiElement {

    @Nullable
    public DLanguageXorExpression getXorExpression();

    @Nullable
    public DLanguageAndExpression getAndExpression();

    @Nullable
    public PsiElement getOP_XOR();

}
