package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageArrayLiteral extends PsiElement {
    @Nullable
    DLanguageArgumentList getArgumentList();

    @Nullable
    PsiElement getOP_BRACKET_RIGHT();

    @Nullable
    PsiElement getOP_BRACKET_LEFT();

}
