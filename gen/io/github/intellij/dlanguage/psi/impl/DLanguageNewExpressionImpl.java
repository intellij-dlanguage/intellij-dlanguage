package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageNewExpressionImpl extends ASTWrapperPsiElement implements DLanguageNewExpression {
    public DLanguageNewExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitNewExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getKW_NEW() {
        return findChildByType(DlangTypes.KW_NEW);
    }

    @Nullable
    public DLanguageNewAnonClassExpression getNewAnonClassExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageNewAnonClassExpression.class);
    }

    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    public DLanguageAssignExpression getAssignExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
    }

    @Nullable
    public DLanguageArguments getArguments() {
        return PsiTreeUtil.getChildOfType(this, DLanguageArguments.class);
    }

    @Nullable
    public PsiElement getOP_BRACKET_LEFT() {
        return findChildByType(DlangTypes.OP_BRACKET_LEFT);
    }

    @Nullable
    public PsiElement getOP_BRACKET_RIGHT() {
        return findChildByType(DlangTypes.OP_BRACKET_RIGHT);
    }

}
