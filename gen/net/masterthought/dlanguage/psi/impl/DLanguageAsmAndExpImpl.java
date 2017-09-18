package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAsmAndExp;
import net.masterthought.dlanguage.psi.DLanguageAsmEqualExp;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_AND;


public class DLanguageAsmAndExpImpl extends ASTWrapperPsiElement implements DLanguageAsmAndExp {
    public DLanguageAsmAndExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitAsmAndExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageAsmAndExp getAsmAndExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmAndExp.class);
    }

    @Nullable
    public DLanguageAsmEqualExp getAsmEqualExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmEqualExp.class);
    }

    @Nullable
    public PsiElement getOP_AND() {
        return findChildByType(OP_AND);
    }

}
