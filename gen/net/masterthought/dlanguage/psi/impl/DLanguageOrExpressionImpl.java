package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageOrExpression;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import net.masterthought.dlanguage.psi.DLanguageXorExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_OR;


public class DLanguageOrExpressionImpl extends ASTWrapperPsiElement implements DLanguageOrExpression {
    public DLanguageOrExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitOrExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageOrExpression getOrExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageOrExpression.class);
    }

    @Nullable
    public DLanguageXorExpression getXorExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageXorExpression.class);
    }

    @Nullable
    public PsiElement getOP_OR() {
        return findChildByType(OP_OR);
    }

}
