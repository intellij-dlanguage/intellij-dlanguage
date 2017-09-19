package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAsmBrExp;
import io.github.intellij.dlanguage.psi.DLanguageAsmMulExp;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.DLanguageAsmBrExp;
import io.github.intellij.dlanguage.psi.DLanguageAsmMulExp;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageAsmMulExpImpl extends ASTWrapperPsiElement implements DLanguageAsmMulExp {
    public DLanguageAsmMulExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAsmMulExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
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
        return findChildByType(DlangTypes.OP_MOD);
    }

    @Nullable
    public PsiElement getOP_DIV() {
        return findChildByType(DlangTypes.OP_DIV);
    }

    @Nullable
    public PsiElement getOP_ASTERISK() {
        return findChildByType(DlangTypes.OP_ASTERISK);
    }

}
