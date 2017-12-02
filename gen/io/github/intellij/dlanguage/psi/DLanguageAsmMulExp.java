package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmMulExp extends PsiElement {

    @Nullable
    DLanguageAsmMulExp getAsmMulExp();

    @Nullable
    DLanguageAsmBrExp getAsmBrExp();

    @Nullable
    PsiElement getOP_MOD();

    @Nullable
    PsiElement getOP_DIV();

    @Nullable
    PsiElement getOP_ASTERISK();

}
