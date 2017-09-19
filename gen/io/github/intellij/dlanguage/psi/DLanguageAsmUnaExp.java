package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmUnaExp extends PsiElement {
    @Nullable
    DLanguageAsmUnaExp getAsmUnaExp();

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageAsmExp getAsmExp();

    @Nullable
    PsiElement getOP_PLUS();

    @Nullable
    PsiElement getOP_MINUS();

    @Nullable
    PsiElement getOP_NOT();

    @Nullable
    PsiElement getOP_TILDA();

    @Nullable
    DLanguageAsmPrimaryExp getAsmPrimaryExp();
}
