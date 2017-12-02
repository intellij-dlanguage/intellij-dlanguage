package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_MINUS;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PLUS;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_TILDA;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAddExpression;
import io.github.intellij.dlanguage.psi.DLanguageMulExpression;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageAddExpressionImpl extends ASTWrapperPsiElement implements
    DLanguageAddExpression {

    public DLanguageAddExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAddExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageAddExpression getAddExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAddExpression.class);
    }

    @Nullable
    public DLanguageMulExpression getMulExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageMulExpression.class);
    }

    @Nullable
    public PsiElement getOP_TILDA() {
        return findChildByType(OP_TILDA);
    }

    @Nullable
    public PsiElement getOP_PLUS() {
        return findChildByType(OP_PLUS);
    }

    @Nullable
    public PsiElement getOP_MINUS() {
        return findChildByType(OP_MINUS);
    }

}
