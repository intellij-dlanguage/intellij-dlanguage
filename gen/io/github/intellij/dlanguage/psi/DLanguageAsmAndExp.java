package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmAndExp extends PsiElement {

    @Nullable
    public DLanguageAsmAndExp getAsmAndExp();

    @Nullable
    public DLanguageAsmEqualExp getAsmEqualExp();

    @Nullable
    public PsiElement getOP_AND();

}
