package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmLogAndExp extends PsiElement {
    @Nullable
    public DLanguageAsmLogAndExp getAsmLogAndExp();

    @Nullable
    public DLanguageAsmOrExp getAsmOrExp();

    @Nullable
    public PsiElement getOP_BOOL_AND();

}
