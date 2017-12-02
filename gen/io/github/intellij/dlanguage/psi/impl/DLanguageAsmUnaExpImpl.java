package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_MINUS;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_NOT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PLUS;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_TILDA;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAsmExp;
import io.github.intellij.dlanguage.psi.DLanguageAsmPrimaryExp;
import io.github.intellij.dlanguage.psi.DLanguageAsmUnaExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageAsmUnaExpImpl extends ASTWrapperPsiElement implements DLanguageAsmUnaExp {

    public DLanguageAsmUnaExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitAsmUnaExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) {
            accept((DLanguageVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageAsmUnaExp getAsmUnaExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmUnaExp.class);
    }

    @Nullable
    public DLanguageIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
    }

    @Nullable
    public DLanguageAsmExp getAsmExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmExp.class);
    }

    @Nullable
    public PsiElement getOP_PLUS() {
        return findChildByType(OP_PLUS);
    }

    @Nullable
    public PsiElement getOP_MINUS() {
        return findChildByType(OP_MINUS);
    }

    @Nullable
    public PsiElement getOP_NOT() {
        return findChildByType(OP_NOT);
    }

    @Nullable
    public PsiElement getOP_TILDA() {
        return findChildByType(OP_TILDA);
    }

    @Nullable
    public DLanguageAsmPrimaryExp getAsmPrimaryExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmPrimaryExp.class);
    }
}
