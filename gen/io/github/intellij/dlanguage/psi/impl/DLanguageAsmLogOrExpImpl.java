package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BOOL_OR;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAsmLogAndExp;
import io.github.intellij.dlanguage.psi.DLanguageAsmLogOrExp;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageAsmLogOrExpImpl extends ASTWrapperPsiElement implements DLanguageAsmLogOrExp {

    public DLanguageAsmLogOrExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAsmLogOrExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
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
