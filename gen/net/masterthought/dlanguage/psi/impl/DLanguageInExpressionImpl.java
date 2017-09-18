package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageInExpression;
import net.masterthought.dlanguage.psi.DLanguageShiftExpression;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.masterthought.dlanguage.psi.DlangTypes.KW_IN;
import static net.masterthought.dlanguage.psi.DlangTypes.OP_NOT;


public class DLanguageInExpressionImpl extends ASTWrapperPsiElement implements DLanguageInExpression {
    public DLanguageInExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitInExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public List<DLanguageShiftExpression> getShiftExpressions() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageShiftExpression.class);
    }

    @Nullable
    public PsiElement getKW_IN() {
        return findChildByType(KW_IN);
    }

    @Nullable
    public PsiElement getOP_NOT() {
        return findChildByType(OP_NOT);
    }

}
