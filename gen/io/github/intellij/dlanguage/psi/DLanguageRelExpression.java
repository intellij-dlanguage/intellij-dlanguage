package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageRelExpression extends PsiElement {

    @Nullable
    DLanguageRelExpression getRelExpression();

    @Nullable
    DLanguageShiftExpression getShiftExpression();

    @Nullable
    PsiElement getOP_GT();

    @Nullable
    PsiElement getOP_GT_EQ();

    @Nullable
    PsiElement getOP_LESS();

    @Nullable
    PsiElement getOP_LESS_EQ();

    @Nullable
    PsiElement getOP_LESS_GR();

    @Nullable
    PsiElement getOP_LESS_GR_EQ();

    @Nullable
    PsiElement getOP_NOT_GR();

    @Nullable
    PsiElement getOP_NOT_GR_EQ();

    @Nullable
    PsiElement getOP_NOT_LESS();

    @Nullable
    PsiElement getOP_NOT_LESS_EQ();

    @Nullable
    PsiElement getOP_UNORD();

    @Nullable
    PsiElement getOP_UNORD_EQ();

}
