package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageSwitchStatement extends PsiElement {

    @Nullable
    PsiElement getKW_SWITCH();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    DLanguageExpression getExpression();

    @Nullable
    DLanguageStatement getStatement();
}
