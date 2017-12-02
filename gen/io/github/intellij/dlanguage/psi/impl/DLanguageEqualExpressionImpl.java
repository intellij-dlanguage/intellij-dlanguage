package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_EQ_EQ;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_NOT_EQ;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageEqualExpression;
import io.github.intellij.dlanguage.psi.DLanguageShiftExpression;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageEqualExpressionImpl extends ASTWrapperPsiElement implements
    DLanguageEqualExpression {

    public DLanguageEqualExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitEqualExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) {
            accept((DLanguageVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @NotNull
    public List<DLanguageShiftExpression> getShiftExpressions() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageShiftExpression.class);
    }

    @Nullable
    public PsiElement getOP_EQ_EQ() {
        return findChildByType(OP_EQ_EQ);
    }

    @Nullable
    public PsiElement getOP_NOT_EQ() {
        return findChildByType(OP_NOT_EQ);
    }

}
