package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;


public interface DLanguageMixinExpression extends PsiElement {

    @NotNull
    DLanguageArgumentList getArgumentList();

    @NotNull
    PsiElement getOP_PAR_RIGHT();

    @NotNull
    PsiElement getOP_PAR_LEFT();

    @NotNull
    PsiElement getKW_MIXIN();

}
