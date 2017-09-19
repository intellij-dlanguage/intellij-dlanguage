package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageOutStatement extends PsiElement {
    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageBlockStatement getBlockStatement();

    @Nullable
    PsiElement getKW_OUT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

}
