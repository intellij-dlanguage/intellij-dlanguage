package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguageRegister extends PsiElement {

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public PsiElement getINTEGER_LITERAL();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

}
