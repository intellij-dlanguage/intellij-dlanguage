package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmLogOrExp extends PsiElement {

    @Nullable
    public DLanguageAsmLogOrExp getAsmLogOrExp();

    @Nullable
    public DLanguageAsmLogAndExp getAsmLogAndExp();

    @Nullable
    public PsiElement getOP_BOOL_OR();

}
