package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAddExpression;
import net.masterthought.dlanguage.psi.DLanguageShiftExpression;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.*;


public class DLanguageShiftExpressionImpl extends ASTWrapperPsiElement implements DLanguageShiftExpression {
    public DLanguageShiftExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitShiftExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageShiftExpression getShiftExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageShiftExpression.class);
    }

    @Nullable
    public DLanguageAddExpression getAddExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAddExpression.class);
    }

    @Nullable
    public PsiElement getOP_SH_RIGHT() {
        return findChildByType(OP_SH_RIGHT);
    }

    @Nullable
    public PsiElement getOP_SH_LEFT() {
        return findChildByType(OP_SH_LEFT);
    }

    @Nullable
    public PsiElement getOP_USH_RIGHT() {
        return findChildByType(OP_USH_RIGHT);
    }

}
