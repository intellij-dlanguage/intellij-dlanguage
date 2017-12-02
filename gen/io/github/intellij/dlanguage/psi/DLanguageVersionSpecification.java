package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageVersionSpecification extends PsiElement {

    @Nullable
    public PsiElement getKW_VERSION();

    @Nullable
    public PsiElement getOP_EQ();

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getINTEGER_LITERAL();

    @Nullable
    public PsiElement getOP_SCOLON();

}
