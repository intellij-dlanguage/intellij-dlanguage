package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAssertExpression;
import net.masterthought.dlanguage.psi.DLanguageStaticAssertStatement;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.KW_STATIC;
import static net.masterthought.dlanguage.psi.DlangTypes.OP_SCOLON;


public class DLanguageStaticAssertStatementImpl extends ASTWrapperPsiElement implements DLanguageStaticAssertStatement {
    public DLanguageStaticAssertStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitStaticAssertStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getKW_STATIC() {
        return findChildByType(KW_STATIC);
    }

    @Nullable
    public DLanguageAssertExpression getAssertExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAssertExpression.class);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }

}
