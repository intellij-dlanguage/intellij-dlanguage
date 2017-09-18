package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageDeleteExpression;
import net.masterthought.dlanguage.psi.DLanguageUnaryExpression;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.KW_DELETE;


public class DLanguageDeleteExpressionImpl extends ASTWrapperPsiElement implements DLanguageDeleteExpression {
    public DLanguageDeleteExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitDeleteExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getKW_DELETE() {
        return findChildByType(KW_DELETE);
    }

    @Nullable
    public DLanguageUnaryExpression getUnaryExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageUnaryExpression.class);
    }
}
