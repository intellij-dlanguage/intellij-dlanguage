package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAliasAssign;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_EQ;

public class DLanguageAliasAssignImpl extends ASTWrapperPsiElement implements
    DLanguageAliasAssign {

    public DLanguageAliasAssignImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAliasAssign(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @NotNull
    public DLanguageType getType() {
        return PsiTreeUtil.findChildOfType(this, DLanguageType.class);
    }

    @NotNull
    public PsiElement getOP_EQ() {
        return findChildByType(OP_EQ);
    }
}
