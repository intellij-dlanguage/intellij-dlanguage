package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmShiftExp extends PsiElement {
    @Nullable
    DLanguageAsmShiftExp getAsmShiftExp();

    @Nullable
    DLanguageAsmAddExp getAsmAddExp();

    @Nullable
    PsiElement getOP_SH_LEFT();

    @Nullable
    PsiElement getOP_SH_RIGHT();

    @Nullable
    PsiElement getOP_USH_RIGHT();

}
