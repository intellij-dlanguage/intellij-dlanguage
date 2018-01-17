package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_PRAGMA;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_COMMA;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PAR_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PAR_RIGHT;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageArgumentList;
import io.github.intellij.dlanguage.psi.DLanguagePragmaExpression;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguagePragmaExpressionImpl extends ASTWrapperPsiElement implements
    DLanguagePragmaExpression {

    public DLanguagePragmaExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitPragmaExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    public DLanguageArgumentList getArgumentList() {
        return PsiTreeUtil.getChildOfType(this, DLanguageArgumentList.class);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(OP_PAR_LEFT);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(OP_PAR_RIGHT);
    }

    @Nullable
    public PsiElement getOP_COMMA() {
        return findChildByType(OP_COMMA);
    }

    @Nullable
    public PsiElement getKW_PRAGMA() {
        return findChildByType(KW_PRAGMA);
    }

}
