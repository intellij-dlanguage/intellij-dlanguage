package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DLanguageIdentityExpression;
import io.github.intellij.dlanguage.psi.DLanguageShiftExpression;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_IS;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_NOT;


public class DLanguageIdentityExpressionImpl extends ASTWrapperPsiElement implements DLanguageIdentityExpression {
    public DLanguageIdentityExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitIdentityExpression(this);
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
    public PsiElement getKW_IS() {
        return findChildByType(DlangTypes.KW_IS);
    }

    @Nullable
    public PsiElement getOP_NOT() {
        return findChildByType(DlangTypes.OP_NOT);
    }

}
