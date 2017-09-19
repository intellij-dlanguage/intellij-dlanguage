package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageInExpression;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DLanguageInExpression;
import io.github.intellij.dlanguage.psi.DLanguageShiftExpression;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_IN;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_NOT;


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
        return findChildByType(DlangTypes.KW_IN);
    }

    @Nullable
    public PsiElement getOP_NOT() {
        return findChildByType(DlangTypes.OP_NOT);
    }

}
