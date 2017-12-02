package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_IS;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_NOT;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageIdentityExpression;
import io.github.intellij.dlanguage.psi.DLanguageShiftExpression;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageIdentityExpressionImpl extends ASTWrapperPsiElement implements
    DLanguageIdentityExpression {

    public DLanguageIdentityExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitIdentityExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @NotNull
    public List<DLanguageShiftExpression> getShiftExpressions() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageShiftExpression.class);
    }

    @Nullable
    public PsiElement getKW_IS() {
        return findChildByType(KW_IS);
    }

    @Nullable
    public PsiElement getOP_NOT() {
        return findChildByType(OP_NOT);
    }

}
