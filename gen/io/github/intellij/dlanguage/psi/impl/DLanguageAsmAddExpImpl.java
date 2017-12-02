package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_MINUS;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PLUS;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAsmAddExp;
import io.github.intellij.dlanguage.psi.DLanguageAsmMulExp;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageAsmAddExpImpl extends ASTWrapperPsiElement implements DLanguageAsmAddExp {

    public DLanguageAsmAddExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAsmAddExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageAsmAddExp getAsmAddExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmAddExp.class);
    }

    @Nullable
    public DLanguageAsmMulExp getAsmMulExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmMulExp.class);
    }

    @Nullable
    public PsiElement getOP_MINUS() {
        return findChildByType(OP_MINUS);
    }

    @Nullable
    public PsiElement getOP_PLUS() {
        return findChildByType(OP_PLUS);
    }

}
