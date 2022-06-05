package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public interface DLanguageInContractExpression extends PsiElement {

    @NotNull
    PsiElement getKW_IN();

    @NotNull
    PsiElement getOP_PAR_LEFT();

    @NotNull
    DLanguageAssertArguments getAssertArguments();

    @NotNull
    PsiElement getOP_PAR_RIGHT();
}
