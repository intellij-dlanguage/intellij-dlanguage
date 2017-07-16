package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmInstruction extends PsiElement {
    @Nullable
    public PsiElement getINTEGER_LITERAL();

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public DLanguageAsmInstruction getAsmInstruction();

    @Nullable
    public DLanguageOperands getOperands();

    @Nullable
    public PsiElement getKW_ALIGN();

    @Nullable
    public PsiElement getOP_COLON();

}
