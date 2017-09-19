package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageBreakStatement extends PsiElement {
    @Nullable
    PsiElement getKW_BREAK();

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_SCOLON();

}
