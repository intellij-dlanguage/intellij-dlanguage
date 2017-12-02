package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageExpressionStatement extends PsiElement {

    @Nullable
    public DLanguageExpression getExpression();

    @Nullable
    public PsiElement getOP_SCOLON();

}
