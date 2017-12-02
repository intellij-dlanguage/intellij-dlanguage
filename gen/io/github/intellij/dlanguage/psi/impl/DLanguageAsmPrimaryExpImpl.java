package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.FLOAT_LITERAL;
import static io.github.intellij.dlanguage.psi.DlangTypes.INTEGER_LITERAL;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_DOLLAR;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAsmExp;
import io.github.intellij.dlanguage.psi.DLanguageAsmPrimaryExp;
import io.github.intellij.dlanguage.psi.DLanguageIdentifierChain;
import io.github.intellij.dlanguage.psi.DLanguageRegister;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageAsmPrimaryExpImpl extends ASTWrapperPsiElement implements
    DLanguageAsmPrimaryExp {

    public DLanguageAsmPrimaryExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAsmPrimaryExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getFLOAT_LITERAL() {
        return findChildByType(FLOAT_LITERAL);
    }

    @Nullable
    public PsiElement getINTEGER_LITERAL() {
        return findChildByType(INTEGER_LITERAL);
    }

    @Nullable
    public DLanguageRegister getRegister() {
        return PsiTreeUtil.getChildOfType(this, DLanguageRegister.class);
    }

    @Nullable
    public DLanguageAsmExp getAsmExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmExp.class);
    }

    @Nullable
    public DLanguageIdentifierChain getIdentifierChain() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierChain.class);
    }

    @Nullable
    public PsiElement getOP_DOLLAR() {
        return findChildByType(OP_DOLLAR);
    }

}
