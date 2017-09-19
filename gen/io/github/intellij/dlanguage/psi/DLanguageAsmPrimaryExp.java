package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmPrimaryExp extends PsiElement {
    @Nullable
    PsiElement getFLOAT_LITERAL();

    @Nullable
    PsiElement getINTEGER_LITERAL();

    @Nullable
    DLanguageRegister getRegister();

    @Nullable
    DLanguageAsmExp getAsmExp();

    @Nullable
    DLanguageIdentifierChain getIdentifierChain();

    @Nullable
    PsiElement getOP_DOLLAR();

}
