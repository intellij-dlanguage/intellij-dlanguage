package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageRelExpression;
import net.masterthought.dlanguage.psi.DLanguageShiftExpression;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.*;


public class DLanguageRelExpressionImpl extends ASTWrapperPsiElement implements DLanguageRelExpression {
    public DLanguageRelExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitRelExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageRelExpression getRelExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageRelExpression.class);
    }

    @Nullable
    public DLanguageShiftExpression getShiftExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageShiftExpression.class);
    }

    @Nullable
    public PsiElement getOP_GT() {
        return findChildByType(OP_GT);
    }

    @Nullable
    public PsiElement getOP_GT_EQ() {
        return findChildByType(OP_GT_EQ);
    }

    @Nullable
    public PsiElement getOP_LESS() {
        return findChildByType(OP_LESS);
    }

    @Nullable
    public PsiElement getOP_LESS_EQ() {
        return findChildByType(OP_LESS_EQ);
    }

    @Nullable
    public PsiElement getOP_LESS_GR() {
        return findChildByType(OP_LESS_GR);
    }

    @Nullable
    public PsiElement getOP_LESS_GR_EQ() {
        return findChildByType(OP_LESS_GR_EQ);
    }

    @Nullable
    public PsiElement getOP_NOT_GR() {
        return findChildByType(OP_NOT_GR);
    }

    @Nullable
    public PsiElement getOP_NOT_GR_EQ() {
        return findChildByType(OP_NOT_GR_EQ);
    }

    @Nullable
    public PsiElement getOP_NOT_LESS() {
        return findChildByType(OP_NOT_LESS);
    }

    @Nullable
    public PsiElement getOP_NOT_LESS_EQ() {
        return findChildByType(OP_NOT_LESS_EQ);
    }

    @Nullable
    public PsiElement getOP_UNORD() {
        return findChildByType(OP_UNORD);
    }

    @Nullable
    public PsiElement getOP_UNORD_EQ() {
        return findChildByType(OP_UNORD_EQ);
    }

}
