package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAndAndExpression;
import net.masterthought.dlanguage.psi.DLanguageOrExpression;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_BOOL_AND;


public class DLanguageAndAndExpressionImpl extends ASTWrapperPsiElement implements DLanguageAndAndExpression {
    public DLanguageAndAndExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAndAndExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageAndAndExpression getAndAndExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAndAndExpression.class);
    }

    @Nullable
    public DLanguageOrExpression getOrExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageOrExpression.class);
    }

    @Nullable
    public PsiElement getOP_BOOL_AND() {
        return findChildByType(OP_BOOL_AND);
    }

}
