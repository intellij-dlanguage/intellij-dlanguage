package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmOrExp extends PsiElement {
    @Nullable
    DLanguageAsmOrExp getAsmOrExp();

    @Nullable
    DLanguageAsmXorExp getAsmXorExp();

    @Nullable
    PsiElement getOP_OR();

}
