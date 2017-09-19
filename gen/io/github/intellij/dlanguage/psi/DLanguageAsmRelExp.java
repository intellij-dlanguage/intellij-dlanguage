package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmRelExp extends PsiElement {
    @Nullable
    DLanguageAsmRelExp getAsmRelExp();

    @Nullable
    DLanguageAsmShiftExp getAsmShiftExp();

    @Nullable
    PsiElement getOP_GT_EQ();

    @Nullable
    PsiElement getOP_GT();

    @Nullable
    PsiElement getOP_LESS();

    @Nullable
    PsiElement getOP_LESS_EQ();

}
