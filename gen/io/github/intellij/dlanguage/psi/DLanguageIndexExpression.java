package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageIndexExpression extends PsiElement {

    @Nullable
    public PsiElement getOP_BRACKET_LEFT();

    @Nullable
    public PsiElement getOP_BRACKET_RIGHT();

    @Nullable
    public DLanguageArgumentList getArgumentList();

    @Nullable
    public DLanguageUnaryExpression getUnaryExpression();
}
