package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAssignExpression extends PsiElement {
    @Nullable
    DLanguageTernaryExpression getTernaryExpression();

    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    PsiElement getOP_AND_EQ();

    @Nullable
    PsiElement getOP_DIV_EQ();

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    PsiElement getOP_EQ_EQ();

    @Nullable
    PsiElement getOP_GT_EQ();

    @Nullable
    PsiElement getOP_LESS_EQ();

    @Nullable
    PsiElement getOP_LESS_GR_EQ();

    @Nullable
    PsiElement getOP_MINUS_EQ();

    @Nullable
    PsiElement getOP_MOD_EQ();

    @Nullable
    PsiElement getOP_MUL_EQ();

    @Nullable
    PsiElement getOP_NOT_EQ();

    @Nullable
    PsiElement getOP_NOT_GR_EQ();

    @Nullable
    PsiElement getOP_NOT_LESS_EQ();

    @Nullable
    PsiElement getOP_OR_EQ();

    @Nullable
    PsiElement getOP_PLUS_EQ();

    @Nullable
    PsiElement getOP_POW_EQ();

    @Nullable
    PsiElement getOP_SH_LEFT_EQ();

    @Nullable
    PsiElement getOP_SH_RIGHT_EQ();

    @Nullable
    PsiElement getOP_TILDA_EQ();

    @Nullable
    PsiElement getOP_UNORD_EQ();

    @Nullable
    PsiElement getOP_USH_RIGHT_EQ();

    @Nullable
    PsiElement getOP_XOR_EQ();

}
