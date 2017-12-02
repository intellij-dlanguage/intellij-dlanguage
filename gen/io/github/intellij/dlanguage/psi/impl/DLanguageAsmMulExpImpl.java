package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_ASTERISK;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_DIV;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_MOD;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAsmBrExp;
import io.github.intellij.dlanguage.psi.DLanguageAsmMulExp;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageAsmMulExpImpl extends ASTWrapperPsiElement implements DLanguageAsmMulExp {

    public DLanguageAsmMulExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAsmMulExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageAsmMulExp getAsmMulExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmMulExp.class);
    }

    @Nullable
    public DLanguageAsmBrExp getAsmBrExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmBrExp.class);
    }

    @Nullable
    public PsiElement getOP_MOD() {
        return findChildByType(OP_MOD);
    }

    @Nullable
    public PsiElement getOP_DIV() {
        return findChildByType(OP_DIV);
    }

    @Nullable
    public PsiElement getOP_ASTERISK() {
        return findChildByType(OP_ASTERISK);
    }

}
