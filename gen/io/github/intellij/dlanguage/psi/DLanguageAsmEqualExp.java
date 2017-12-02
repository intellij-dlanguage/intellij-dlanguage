package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmEqualExp extends PsiElement {

    @Nullable
    public DLanguageAsmEqualExp getAsmEqualExp();

    @Nullable
    public DLanguageAsmRelExp getAsmRelExp();

    @Nullable
    public PsiElement getOP_NOT_EQ();

    @Nullable
    public PsiElement getOP_EQ_EQ();

}
