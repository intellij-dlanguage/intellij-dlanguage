package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmAddExp extends PsiElement {

    @Nullable
    public DLanguageAsmAddExp getAsmAddExp();

    @Nullable
    public DLanguageAsmMulExp getAsmMulExp();

    @Nullable
    public PsiElement getOP_MINUS();

    @Nullable
    public PsiElement getOP_PLUS();

}
