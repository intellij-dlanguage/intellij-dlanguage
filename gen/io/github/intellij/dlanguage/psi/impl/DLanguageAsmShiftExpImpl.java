package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_SH_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_SH_RIGHT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_USH_RIGHT;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAsmAddExp;
import io.github.intellij.dlanguage.psi.DLanguageAsmShiftExp;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageAsmShiftExpImpl extends ASTWrapperPsiElement implements DLanguageAsmShiftExp {

    public DLanguageAsmShiftExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAsmShiftExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageAsmShiftExp getAsmShiftExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmShiftExp.class);
    }

    @Nullable
    public DLanguageAsmAddExp getAsmAddExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmAddExp.class);
    }

    @Nullable
    public PsiElement getOP_SH_LEFT() {
        return findChildByType(OP_SH_LEFT);
    }

    @Nullable
    public PsiElement getOP_SH_RIGHT() {
        return findChildByType(OP_SH_RIGHT);
    }

    @Nullable
    public PsiElement getOP_USH_RIGHT() {
        return findChildByType(OP_USH_RIGHT);
    }

}
