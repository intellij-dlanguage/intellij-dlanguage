package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_COLON;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_QUEST;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageExpression;
import io.github.intellij.dlanguage.psi.DLanguageOrOrExpression;
import io.github.intellij.dlanguage.psi.DLanguageTernaryExpression;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageTernaryExpressionImpl extends ASTWrapperPsiElement implements
    DLanguageTernaryExpression {

    public DLanguageTernaryExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTernaryExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getOP_QUEST() {
        return findChildByType(OP_QUEST);
    }

    @Nullable
    public PsiElement getOP_COLON() {
        return findChildByType(OP_COLON);
    }

    @Nullable
    public DLanguageOrOrExpression getOrOrExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageOrOrExpression.class);
    }

    @Nullable
    public DLanguageExpression getExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageExpression.class);
    }

    @Nullable
    public DLanguageTernaryExpression getTernaryExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTernaryExpression.class);
    }
}
