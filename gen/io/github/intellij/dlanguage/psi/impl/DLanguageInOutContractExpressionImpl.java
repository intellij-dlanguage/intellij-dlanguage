package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageInContractExpression;
import io.github.intellij.dlanguage.psi.DLanguageInOutContractExpression;
import io.github.intellij.dlanguage.psi.DLanguageOutContractExpression;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DLanguageInOutContractExpressionImpl extends ASTWrapperPsiElement implements
    DLanguageInOutContractExpression {

    public DLanguageInOutContractExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitInOutContractExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageInContractExpression getInContractExpression() {
        return PsiTreeUtil.findChildOfType(this, DLanguageInContractExpression.class);
    }

    @Nullable
    public DLanguageOutContractExpression getOutContractExpression() {
        return PsiTreeUtil.findChildOfType(this, DLanguageOutContractExpression.class);
    }
}
