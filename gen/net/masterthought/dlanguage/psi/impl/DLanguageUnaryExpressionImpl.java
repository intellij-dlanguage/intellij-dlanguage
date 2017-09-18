package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.*;


public class DLanguageUnaryExpressionImpl extends ASTWrapperPsiElement implements DLanguageUnaryExpression {
    public DLanguageUnaryExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitUnaryExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguagePrimaryExpression getPrimaryExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguagePrimaryExpression.class);
    }

    @Nullable
    public DLanguageFunctionCallExpression getFunctionCallExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageFunctionCallExpression.class);
    }

    @Nullable
    public DLanguageUnaryExpression getUnaryExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageUnaryExpression.class);
    }

    @Nullable
    public DLanguageNewExpression getNewExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageNewExpression.class);
    }

    @Nullable
    public DLanguageDeleteExpression getDeleteExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDeleteExpression.class);
    }

    @Nullable
    public DLanguageCastExpression getCastExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageCastExpression.class);
    }

    @Nullable
    public DLanguageAssertExpression getAssertExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAssertExpression.class);
    }

    @Nullable
    public DLanguageIdentifierOrTemplateInstance getIdentifierOrTemplateInstance() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierOrTemplateInstance.class);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(OP_PAR_RIGHT);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(OP_PAR_LEFT);
    }

    @Nullable
    public DLanguageSliceExpression getSliceExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageSliceExpression.class);
    }

    @Nullable
    public DLanguageIndexExpression getIndexExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIndexExpression.class);
    }

    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    public PsiElement getOP_DOT() {
        return findChildByType(OP_DOT);
    }

    @Nullable
    public PsiElement getOP_AND() {
        return findChildByType(OP_AND);
    }

    @Nullable
    public PsiElement getOP_ASTERISK() {
        return findChildByType(OP_ASTERISK);
    }

    @Nullable
    public PsiElement getOP_MINUS() {
        return findChildByType(OP_MINUS);
    }

    @Nullable
    public PsiElement getOP_MINUS_MINUS() {
        return findChildByType(OP_MINUS_MINUS);
    }

    @Nullable
    public PsiElement getOP_NOT() {
        return findChildByType(OP_NOT);
    }

    @Nullable
    public PsiElement getOP_PLUS() {
        return findChildByType(OP_PLUS);
    }

    @Nullable
    public PsiElement getOP_PLUS_PLUS() {
        return findChildByType(OP_PLUS_PLUS);
    }

    @Nullable
    public PsiElement getOP_TILDA() {
        return findChildByType(OP_TILDA);
    }

}
