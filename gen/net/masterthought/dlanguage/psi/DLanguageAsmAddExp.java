package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmAddExp extends PsiElement {
    @Nullable
    DLanguageAsmAddExp getAsmAddExp();

    @Nullable
    DLanguageAsmMulExp getAsmMulExp();

    @Nullable
    PsiElement getOP_MINUS();

    @Nullable
    PsiElement getOP_PLUS();

}
