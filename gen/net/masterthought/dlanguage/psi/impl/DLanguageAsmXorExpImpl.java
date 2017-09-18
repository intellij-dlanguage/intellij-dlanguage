package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAsmAndExp;
import net.masterthought.dlanguage.psi.DLanguageAsmXorExp;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_XOR;


public class DLanguageAsmXorExpImpl extends ASTWrapperPsiElement implements DLanguageAsmXorExp {
    public DLanguageAsmXorExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAsmXorExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageAsmXorExp getAsmXorExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmXorExp.class);
    }

    @Nullable
    public DLanguageAsmAndExp getAsmAndExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmAndExp.class);
    }

    @Nullable
    public PsiElement getOP_XOR() {
        return findChildByType(OP_XOR);
    }

}
