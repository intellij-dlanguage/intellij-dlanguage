package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_GT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_GT_EQ;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_LESS;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_LESS_EQ;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAsmRelExp;
import io.github.intellij.dlanguage.psi.DLanguageAsmShiftExp;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageAsmRelExpImpl extends ASTWrapperPsiElement implements DLanguageAsmRelExp {

    public DLanguageAsmRelExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAsmRelExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageAsmRelExp getAsmRelExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmRelExp.class);
    }

    @Nullable
    public DLanguageAsmShiftExp getAsmShiftExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmShiftExp.class);
    }

    @Nullable
    public PsiElement getOP_GT_EQ() {
        return findChildByType(OP_GT_EQ);
    }

    @Nullable
    public PsiElement getOP_GT() {
        return findChildByType(OP_GT);
    }

    @Nullable
    public PsiElement getOP_LESS() {
        return findChildByType(OP_LESS);
    }

    @Nullable
    public PsiElement getOP_LESS_EQ() {
        return findChildByType(OP_LESS_EQ);
    }

}
