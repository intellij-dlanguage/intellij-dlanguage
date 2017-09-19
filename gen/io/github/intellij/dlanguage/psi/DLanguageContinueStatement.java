package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageContinueStatement extends PsiElement {
    @Nullable
    PsiElement getKW_CONTINUE();

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_SCOLON();

}
