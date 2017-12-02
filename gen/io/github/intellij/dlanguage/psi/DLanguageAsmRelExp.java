package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmRelExp extends PsiElement {

    @Nullable
    public DLanguageAsmRelExp getAsmRelExp();

    @Nullable
    public DLanguageAsmShiftExp getAsmShiftExp();

    @Nullable
    public PsiElement getOP_GT_EQ();

    @Nullable
    public PsiElement getOP_GT();

    @Nullable
    public PsiElement getOP_LESS();

    @Nullable
    public PsiElement getOP_LESS_EQ();

}
