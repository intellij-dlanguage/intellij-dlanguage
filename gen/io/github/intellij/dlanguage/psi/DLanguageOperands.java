package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageOperands extends PsiElement {
    @Nullable
    PsiElement getOP_COMMA();

    @Nullable
    DLanguageOperands getOperands();

    @Nullable
    DLanguageAsmExp getAsmExp();
}
