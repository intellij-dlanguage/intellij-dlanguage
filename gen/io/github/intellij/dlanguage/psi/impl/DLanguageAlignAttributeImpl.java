package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAlignAttribute;
import io.github.intellij.dlanguage.psi.DLanguageAssignExpression;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.DLanguageAlignAttribute;
import io.github.intellij.dlanguage.psi.DLanguageAssignExpression;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageAlignAttributeImpl extends ASTWrapperPsiElement implements DLanguageAlignAttribute {
    public DLanguageAlignAttributeImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAlignAttribute(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getKW_ALIGN() {
        return findChildByType(DlangTypes.KW_ALIGN);
    }

    @Nullable
    public DLanguageAssignExpression getAssignExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(DlangTypes.OP_PAR_RIGHT);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(DlangTypes.OP_PAR_LEFT);
    }

}
