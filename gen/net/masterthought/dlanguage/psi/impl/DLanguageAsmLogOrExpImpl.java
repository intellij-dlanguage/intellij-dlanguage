package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAsmLogAndExp;
import net.masterthought.dlanguage.psi.DLanguageAsmLogOrExp;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_BOOL_OR;


public class DLanguageAsmLogOrExpImpl extends ASTWrapperPsiElement implements DLanguageAsmLogOrExp {
    public DLanguageAsmLogOrExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitAsmLogOrExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageAsmLogOrExp getAsmLogOrExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmLogOrExp.class);
    }

    @Nullable
    public DLanguageAsmLogAndExp getAsmLogAndExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmLogAndExp.class);
    }

    @Nullable
    public PsiElement getOP_BOOL_OR() {
        return findChildByType(OP_BOOL_OR);
    }

}
