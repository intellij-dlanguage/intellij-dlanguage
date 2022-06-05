package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DLanguageOutContractExpression extends PsiElement {

    @NotNull
    PsiElement getKW_OUT();

    @NotNull
    PsiElement getOP_PAR_LEFT();

    @NotNull
    PsiElement getSEMICOLON();

    @NotNull
    DLanguageAssertArguments getAssertArguments();

    @NotNull
    PsiElement getOP_PAR_RIGHT();
}
