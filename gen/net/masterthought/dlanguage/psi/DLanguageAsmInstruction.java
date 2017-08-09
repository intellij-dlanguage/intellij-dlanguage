package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmInstruction extends PsiElement {
    @Nullable
    PsiElement getINTEGER_LITERAL();

    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    DLanguageAsmInstruction getAsmInstruction();

    @Nullable
    DLanguageOperands getOperands();

    @Nullable
    PsiElement getKW_ALIGN();

    @Nullable
    PsiElement getOP_COLON();

}
