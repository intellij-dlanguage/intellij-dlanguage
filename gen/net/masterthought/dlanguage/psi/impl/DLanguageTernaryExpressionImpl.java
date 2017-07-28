package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageExpression;
import net.masterthought.dlanguage.psi.DLanguageOrOrExpression;
import net.masterthought.dlanguage.psi.DLanguageTernaryExpression;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_COLON;
import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_QUEST;


public class DLanguageTernaryExpressionImpl extends ASTWrapperPsiElement implements DLanguageTernaryExpression {
    public DLanguageTernaryExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitTernaryExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
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
