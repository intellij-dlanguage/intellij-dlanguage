package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmXorExp extends PsiElement {

    @Nullable
    public DLanguageAsmXorExp getAsmXorExp();

    @Nullable
    public DLanguageAsmAndExp getAsmAndExp();

    @Nullable
    public PsiElement getOP_XOR();

}
