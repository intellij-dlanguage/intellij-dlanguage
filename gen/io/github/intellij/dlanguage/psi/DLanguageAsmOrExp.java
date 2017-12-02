package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmOrExp extends PsiElement {

    @Nullable
    public DLanguageAsmOrExp getAsmOrExp();

    @Nullable
    public DLanguageAsmXorExp getAsmXorExp();

    @Nullable
    public PsiElement getOP_OR();

}
