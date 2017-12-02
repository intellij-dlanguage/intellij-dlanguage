package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_EQ_EQ;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_NOT_EQ;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAsmEqualExp;
import io.github.intellij.dlanguage.psi.DLanguageAsmRelExp;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageAsmEqualExpImpl extends ASTWrapperPsiElement implements DLanguageAsmEqualExp {

    public DLanguageAsmEqualExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAsmEqualExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageAsmEqualExp getAsmEqualExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmEqualExp.class);
    }

    @Nullable
    public DLanguageAsmRelExp getAsmRelExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmRelExp.class);
    }

    @Nullable
    public PsiElement getOP_NOT_EQ() {
        return findChildByType(OP_NOT_EQ);
    }

    @Nullable
    public PsiElement getOP_EQ_EQ() {
        return findChildByType(OP_EQ_EQ);
    }

}
