package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDebugCondition extends PsiElement {

    @Nullable
    public PsiElement getKW_DEBUG();

    @Nullable
    public PsiElement getINTEGER_LITERAL();

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

}
