package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAssignExpression extends PsiElement {

    @Nullable
    public DLanguageTernaryExpression getTernaryExpression();

    @Nullable
    public DLanguageAssignExpression getAssignExpression();

    @Nullable
    public PsiElement getOP_AND_EQ();

    @Nullable
    public PsiElement getOP_DIV_EQ();

    @Nullable
    public PsiElement getOP_EQ();

    @Nullable
    public PsiElement getOP_EQ_EQ();

    @Nullable
    public PsiElement getOP_GT_EQ();

    @Nullable
    public PsiElement getOP_LESS_EQ();

    @Nullable
    public PsiElement getOP_LESS_GR_EQ();

    @Nullable
    public PsiElement getOP_MINUS_EQ();

    @Nullable
    public PsiElement getOP_MOD_EQ();

    @Nullable
    public PsiElement getOP_MUL_EQ();

    @Nullable
    public PsiElement getOP_NOT_EQ();

    @Nullable
    public PsiElement getOP_NOT_GR_EQ();

    @Nullable
    public PsiElement getOP_NOT_LESS_EQ();

    @Nullable
    public PsiElement getOP_OR_EQ();

    @Nullable
    public PsiElement getOP_PLUS_EQ();

    @Nullable
    public PsiElement getOP_POW_EQ();

    @Nullable
    public PsiElement getOP_SH_LEFT_EQ();

    @Nullable
    public PsiElement getOP_SH_RIGHT_EQ();

    @Nullable
    public PsiElement getOP_TILDA_EQ();

    @Nullable
    public PsiElement getOP_UNORD_EQ();

    @Nullable
    public PsiElement getOP_USH_RIGHT_EQ();

    @Nullable
    public PsiElement getOP_XOR_EQ();

}
