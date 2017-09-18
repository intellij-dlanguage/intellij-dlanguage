package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAsmExp;
import net.masterthought.dlanguage.psi.DLanguageOperands;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_COMMA;


public class DLanguageOperandsImpl extends ASTWrapperPsiElement implements DLanguageOperands {
    public DLanguageOperandsImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitOperands(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getOP_COMMA() {
        return findChildByType(OP_COMMA);
    }

    @Nullable
    public DLanguageOperands getOperands() {
        return PsiTreeUtil.getChildOfType(this, DLanguageOperands.class);
    }

    @Nullable
    public DLanguageAsmExp getAsmExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmExp.class);
    }
}
