package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmMulExp extends PsiElement {

    @Nullable
    public DLanguageAsmMulExp getAsmMulExp();

    @Nullable
    public DLanguageAsmBrExp getAsmBrExp();

    @Nullable
    public PsiElement getOP_MOD();

    @Nullable
    public PsiElement getOP_DIV();

    @Nullable
    public PsiElement getOP_ASTERISK();

}
