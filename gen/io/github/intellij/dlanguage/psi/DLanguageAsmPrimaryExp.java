package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAsmPrimaryExp extends PsiElement {

    @Nullable
    public PsiElement getFLOAT_LITERAL();

    @Nullable
    public PsiElement getINTEGER_LITERAL();

    @Nullable
    public DLanguageRegister getRegister();

    @Nullable
    public DLanguageAsmExp getAsmExp();

    @Nullable
    public DLanguageIdentifierChain getIdentifierChain();

    @Nullable
    public PsiElement getOP_DOLLAR();

}
