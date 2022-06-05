package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DLanguagePragmaStatement extends PsiElement {

    @NotNull
    DLanguagePragmaExpression getPragmaExpression();

    @Nullable
    DLanguageBlockStatement getBlockStatement();

    @Nullable
    PsiElement getSEMICOLON();

    @Nullable
    DLanguageStatement getStatement();
}
