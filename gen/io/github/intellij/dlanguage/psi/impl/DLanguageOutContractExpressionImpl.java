package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAssertArguments;
import io.github.intellij.dlanguage.psi.DLanguageOutContractExpression;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;

public class DLanguageOutContractExpressionImpl extends ASTWrapperPsiElement implements
    DLanguageOutContractExpression {

    public DLanguageOutContractExpressionImpl(ASTNode node) {
        super(node);
    }
    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitOutContractExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @NotNull
    public PsiElement getKW_OUT() {
        return findChildByType(KW_OUT);
    }

    @NotNull
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(OP_PAR_LEFT);
    }

    @NotNull
    public PsiElement getSEMICOLON() {
        return findChildByType(SEMICOLON);
    }

    @NotNull
    public DLanguageAssertArguments getAssertArguments() {
        return PsiTreeUtil.findChildOfType(this, DLanguageAssertArguments.class);
    }

    @NotNull
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(OP_PAR_RIGHT);
    }
}
