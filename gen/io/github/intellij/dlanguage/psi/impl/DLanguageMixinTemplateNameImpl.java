package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_DOT;


public class DLanguageMixinTemplateNameImpl extends ASTWrapperPsiElement implements DLanguageMixinTemplateName {
    public DLanguageMixinTemplateNameImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitMixinTemplateName(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageTypeofExpression getTypeofExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTypeofExpression.class);
    }

    @Nullable
    public DLanguageIdentifierOrTemplateChain getIdentifierOrTemplateChain() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierOrTemplateChain.class);
    }

    @Nullable
    public DLanguageSymbol getSymbol() {
        return PsiTreeUtil.getChildOfType(this, DLanguageSymbol.class);
    }

    @Nullable
    public PsiElement getOP_DOT() {
        return findChildByType(DlangTypes.OP_DOT);
    }

}
