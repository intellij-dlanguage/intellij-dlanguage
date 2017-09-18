package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAndExpression;
import net.masterthought.dlanguage.psi.DlangVisitor;
import net.masterthought.dlanguage.psi.DLanguageXorExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_XOR;


public class DLanguageXorExpressionImpl extends ASTWrapperPsiElement implements DLanguageXorExpression {
    public DLanguageXorExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitXorExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageXorExpression getXorExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageXorExpression.class);
    }

    @Nullable
    public DLanguageAndExpression getAndExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAndExpression.class);
    }

    @Nullable
    public PsiElement getOP_XOR() {
        return findChildByType(OP_XOR);
    }

}
