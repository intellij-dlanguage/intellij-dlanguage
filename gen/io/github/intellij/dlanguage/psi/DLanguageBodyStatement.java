package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageBodyStatement extends PsiElement {
    @Nullable
    PsiElement getKW_BODY();

    @Nullable
    DLanguageBlockStatement getBlockStatement();
}
