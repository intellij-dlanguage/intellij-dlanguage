package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageRelExpression extends PsiElement {

    @Nullable
    public DLanguageRelExpression getRelExpression();

    @Nullable
    public DLanguageShiftExpression getShiftExpression();

    @Nullable
    public PsiElement getOP_GT();

    @Nullable
    public PsiElement getOP_GT_EQ();

    @Nullable
    public PsiElement getOP_LESS();

    @Nullable
    public PsiElement getOP_LESS_EQ();

    @Nullable
    public PsiElement getOP_LESS_GR();

    @Nullable
    public PsiElement getOP_LESS_GR_EQ();

    @Nullable
    public PsiElement getOP_NOT_GR();

    @Nullable
    public PsiElement getOP_NOT_GR_EQ();

    @Nullable
    public PsiElement getOP_NOT_LESS();

    @Nullable
    public PsiElement getOP_NOT_LESS_EQ();

    @Nullable
    public PsiElement getOP_UNORD();

    @Nullable
    public PsiElement getOP_UNORD_EQ();

}
