package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageInStatement extends PsiElement {

    @Nullable
    public PsiElement getKW_IN();

    @Nullable
    public DLanguageBlockStatement getBlockStatement();
}
