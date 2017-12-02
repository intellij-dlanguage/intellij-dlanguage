package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACKET_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACKET_RIGHT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_DDOT;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAssignExpression;
import io.github.intellij.dlanguage.psi.DLanguageSliceExpression;
import io.github.intellij.dlanguage.psi.DLanguageUnaryExpression;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageSliceExpressionImpl extends ASTWrapperPsiElement implements
    DLanguageSliceExpression {

    public DLanguageSliceExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitSliceExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageUnaryExpression getUnaryExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageUnaryExpression.class);
    }

    @NotNull
    public List<DLanguageAssignExpression> getAssignExpressions() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAssignExpression.class);
    }

    @Nullable
    public PsiElement getOP_BRACKET_LEFT() {
        return findChildByType(OP_BRACKET_LEFT);
    }

    @Nullable
    public PsiElement getOP_BRACKET_RIGHT() {
        return findChildByType(OP_BRACKET_RIGHT);
    }

    @Nullable
    public PsiElement getOP_DDOT() {
        return findChildByType(OP_DDOT);
    }

}
