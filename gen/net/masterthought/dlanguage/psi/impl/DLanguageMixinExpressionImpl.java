package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAssignExpression;
import net.masterthought.dlanguage.psi.DLanguageMixinExpression;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.*;


public class DLanguageMixinExpressionImpl extends ASTWrapperPsiElement implements DLanguageMixinExpression {
    public DLanguageMixinExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitMixinExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageAssignExpression getAssignExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
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
    public PsiElement getKW_MIXIN() {
        return findChildByType(KW_MIXIN);
    }

}
