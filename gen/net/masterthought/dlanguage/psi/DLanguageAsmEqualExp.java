package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmEqualExp extends PsiElement {
    @Nullable
    DLanguageAsmEqualExp getAsmEqualExp();

    @Nullable
    DLanguageAsmRelExp getAsmRelExp();

    @Nullable
    PsiElement getOP_NOT_EQ();

    @Nullable
    PsiElement getOP_EQ_EQ();

}
