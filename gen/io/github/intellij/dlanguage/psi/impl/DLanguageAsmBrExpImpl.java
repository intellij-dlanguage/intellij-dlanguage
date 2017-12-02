package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACKET_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACKET_RIGHT;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAsmBrExp;
import io.github.intellij.dlanguage.psi.DLanguageAsmExp;
import io.github.intellij.dlanguage.psi.DLanguageAsmUnaExp;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageAsmBrExpImpl extends ASTWrapperPsiElement implements DLanguageAsmBrExp {

    public DLanguageAsmBrExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAsmBrExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageAsmExp getAsmExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmExp.class);
    }

    @Nullable
    public DLanguageAsmUnaExp getAsmUnaExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmUnaExp.class);
    }

    @Nullable
    public DLanguageAsmBrExp getAsmBrExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmBrExp.class);
    }

    @Nullable
    public PsiElement getOP_BRACKET_RIGHT() {
        return findChildByType(OP_BRACKET_RIGHT);
    }

    @Nullable
    public PsiElement getOP_BRACKET_LEFT() {
        return findChildByType(OP_BRACKET_LEFT);
    }

}
