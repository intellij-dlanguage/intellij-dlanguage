package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAssertArguments;
import io.github.intellij.dlanguage.psi.DLanguageInContractExpression;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;

public class DLanguageInContractExpressionImpl extends ASTWrapperPsiElement implements
    DLanguageInContractExpression {

    public DLanguageInContractExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitInContractExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @NotNull
    public PsiElement getKW_IN() {
        return findChildByType(KW_IN);
    }

    @NotNull
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(OP_PAR_LEFT);
    }

    @NotNull
    public DLanguageAssertArguments getAssertArguments() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAssertArguments.class);
    }

    @NotNull
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(OP_PAR_RIGHT);
    }
}
