package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_AND;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAsmAndExp;
import io.github.intellij.dlanguage.psi.DLanguageAsmEqualExp;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageAsmAndExpImpl extends ASTWrapperPsiElement implements DLanguageAsmAndExp {

    public DLanguageAsmAndExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAsmAndExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
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
