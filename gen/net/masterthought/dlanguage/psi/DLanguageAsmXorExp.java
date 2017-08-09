package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmXorExp extends PsiElement {
    @Nullable
    DLanguageAsmXorExp getAsmXorExp();

    @Nullable
    DLanguageAsmAndExp getAsmAndExp();

    @Nullable
    PsiElement getOP_XOR();

}
