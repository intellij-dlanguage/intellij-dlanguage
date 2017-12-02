package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmAndExp extends PsiElement {

    @Nullable
    DLanguageAsmAndExp getAsmAndExp();

    @Nullable
    DLanguageAsmEqualExp getAsmEqualExp();

    @Nullable
    PsiElement getOP_AND();

}
