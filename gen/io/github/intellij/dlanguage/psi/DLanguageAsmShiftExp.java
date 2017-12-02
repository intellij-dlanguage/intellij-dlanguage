package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmShiftExp extends PsiElement {

    @Nullable
    public DLanguageAsmShiftExp getAsmShiftExp();

    @Nullable
    public DLanguageAsmAddExp getAsmAddExp();

    @Nullable
    public PsiElement getOP_SH_LEFT();

    @Nullable
    public PsiElement getOP_SH_RIGHT();

    @Nullable
    public PsiElement getOP_USH_RIGHT();

}
