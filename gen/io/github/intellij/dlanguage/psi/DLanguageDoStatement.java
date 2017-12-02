package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDoStatement extends PsiElement {

    @Nullable
    PsiElement getKW_DO();

    @Nullable
    PsiElement getKW_WHILE();

    @Nullable
    DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault();

    @Nullable
    DLanguageExpression getExpression();

    @Nullable
    PsiElement getOP_SCOLON();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

}
