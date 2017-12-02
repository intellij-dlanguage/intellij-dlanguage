package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDoStatement extends PsiElement {

    @Nullable
    public PsiElement getKW_DO();

    @Nullable
    public PsiElement getKW_WHILE();

    @Nullable
    public DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault();

    @Nullable
    public DLanguageExpression getExpression();

    @Nullable
    public PsiElement getOP_SCOLON();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

}
