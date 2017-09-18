package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageMulExpression;
import net.masterthought.dlanguage.psi.DLanguagePowExpression;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.*;


public class DLanguageMulExpressionImpl extends ASTWrapperPsiElement implements DLanguageMulExpression {
    public DLanguageMulExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitMulExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageMulExpression getMulExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageMulExpression.class);
    }

    @Nullable
    public DLanguagePowExpression getPowExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguagePowExpression.class);
    }

    @Nullable
    public PsiElement getOP_MOD() {
        return findChildByType(OP_MOD);
    }

    @Nullable
    public PsiElement getOP_DIV() {
        return findChildByType(OP_DIV);
    }

    @Nullable
    public PsiElement getOP_ASTERISK() {
        return findChildByType(OP_ASTERISK);
    }

}
