package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_AND;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAndExpression;
import io.github.intellij.dlanguage.psi.DLanguageCmpExpression;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageAndExpressionImpl extends ASTWrapperPsiElement implements
    DLanguageAndExpression {

    public DLanguageAndExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAndExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageAndExpression getAndExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAndExpression.class);
    }

    @Nullable
    public DLanguageCmpExpression getCmpExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageCmpExpression.class);
    }

    @Nullable
    public PsiElement getOP_AND() {
        return findChildByType(OP_AND);
    }

}
