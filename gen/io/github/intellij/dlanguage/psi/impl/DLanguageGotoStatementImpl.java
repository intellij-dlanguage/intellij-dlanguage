package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageExpression;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DLanguageExpression;
import io.github.intellij.dlanguage.psi.DLanguageGotoStatement;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageGotoStatementImpl extends ASTWrapperPsiElement implements DLanguageGotoStatement {
    public DLanguageGotoStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitGotoStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    public DLanguageExpression getExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageExpression.class);
    }

    @Nullable
    public PsiElement getKW_DEFAULT() {
        return findChildByType(DlangTypes.KW_DEFAULT);
    }

    @Nullable
    public PsiElement getKW_CASE() {
        return findChildByType(DlangTypes.KW_CASE);
    }

    @Nullable
    public PsiElement getKW_GOTO() {
        return findChildByType(DlangTypes.KW_GOTO);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(DlangTypes.OP_SCOLON);
    }

}
