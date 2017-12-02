package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageBodyStatement extends PsiElement {

    @Nullable
    public PsiElement getKW_BODY();

    @Nullable
    public DLanguageBlockStatement getBlockStatement();
}
