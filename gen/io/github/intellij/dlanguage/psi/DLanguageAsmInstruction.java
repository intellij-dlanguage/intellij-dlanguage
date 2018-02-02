package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmInstruction extends PsiElement {

    @Nullable
    public PsiElement getINTEGER_LITERAL();

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public DLanguageAsmInstruction getAsmInstruction();

    @Nullable
    public DLanguageOperands getOperands();

    @Nullable
    public PsiElement getKW_ALIGN();

    @Nullable
    public PsiElement getOP_COLON();

}
