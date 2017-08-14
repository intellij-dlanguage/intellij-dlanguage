package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmLogOrExp extends PsiElement {
    @Nullable
    DLanguageAsmLogOrExp getAsmLogOrExp();

    @Nullable
    DLanguageAsmLogAndExp getAsmLogAndExp();

    @Nullable
    PsiElement getOP_BOOL_OR();

}
