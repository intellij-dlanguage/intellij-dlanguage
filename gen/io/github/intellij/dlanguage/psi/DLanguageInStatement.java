package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageInStatement extends PsiElement {
    @Nullable
    PsiElement getKW_IN();

    @Nullable
    DLanguageBlockStatement getBlockStatement();
}
