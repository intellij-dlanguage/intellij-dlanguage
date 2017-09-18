package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAsmAddExp;
import net.masterthought.dlanguage.psi.DLanguageAsmMulExp;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_MINUS;
import static net.masterthought.dlanguage.psi.DlangTypes.OP_PLUS;


public class DLanguageAsmAddExpImpl extends ASTWrapperPsiElement implements DLanguageAsmAddExp {
    public DLanguageAsmAddExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitAsmAddExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageAsmAddExp getAsmAddExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmAddExp.class);
    }

    @Nullable
    public DLanguageAsmMulExp getAsmMulExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmMulExp.class);
    }

    @Nullable
    public PsiElement getOP_MINUS() {
        return findChildByType(OP_MINUS);
    }

    @Nullable
    public PsiElement getOP_PLUS() {
        return findChildByType(OP_PLUS);
    }

}
