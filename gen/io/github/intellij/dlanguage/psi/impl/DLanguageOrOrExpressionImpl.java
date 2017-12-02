package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BOOL_OR;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAndAndExpression;
import io.github.intellij.dlanguage.psi.DLanguageOrOrExpression;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageOrOrExpressionImpl extends ASTWrapperPsiElement implements
    DLanguageOrOrExpression {

    public DLanguageOrOrExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitOrOrExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageAndAndExpression getAndAndExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAndAndExpression.class);
    }

    @Nullable
    public DLanguageOrOrExpression getOrOrExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageOrOrExpression.class);
    }

    @Nullable
    public PsiElement getOP_BOOL_OR() {
        return findChildByType(OP_BOOL_OR);
    }

}
