package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmExp extends PsiElement {
    @Nullable
    DLanguageAsmLogOrExp getAsmLogOrExp();

    @Nullable
    DLanguageAsmExp getAsmExp();

    @Nullable
    PsiElement getOP_QUEST();

    @Nullable
    PsiElement getOP_COLON();

}
