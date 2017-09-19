package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageExpression;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DLanguageExpression;
import io.github.intellij.dlanguage.psi.DLanguageExpressionStatement;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_SCOLON;


public class DLanguageExpressionStatementImpl extends ASTWrapperPsiElement implements DLanguageExpressionStatement {
    public DLanguageExpressionStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitExpressionStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageExpression getExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageExpression.class);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(DlangTypes.OP_SCOLON);
    }

}
