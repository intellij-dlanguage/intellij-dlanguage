package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAsmAddExp;
import net.masterthought.dlanguage.psi.DLanguageAsmShiftExp;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DLanguageTypes.*;


public class DLanguageAsmShiftExpImpl extends ASTWrapperPsiElement implements DLanguageAsmShiftExp {
    public DLanguageAsmShiftExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitAsmShiftExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
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
