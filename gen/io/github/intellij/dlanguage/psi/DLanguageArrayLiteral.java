package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageArrayLiteral extends PsiElement {

    @Nullable
    public DLanguageArgumentList getArgumentList();

    @Nullable
    public PsiElement getOP_BRACKET_RIGHT();

    @Nullable
    public PsiElement getOP_BRACKET_LEFT();

}
