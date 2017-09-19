package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DLanguageIdentifierChain;
import io.github.intellij.dlanguage.psi.DLanguageLinkageAttribute;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageLinkageAttributeImpl extends ASTWrapperPsiElement implements DLanguageLinkageAttribute {
    public DLanguageLinkageAttributeImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitLinkageAttribute(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageIdentifierChain getIdentifierChain() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierChain.class);
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(DlangTypes.OP_PAR_RIGHT);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(DlangTypes.OP_PAR_LEFT);
    }

    @Nullable
    public PsiElement getOP_PLUS_PLUS() {
        return findChildByType(DlangTypes.OP_PLUS_PLUS);
    }

    @Nullable
    public PsiElement getKW_EXTERN() {
        return findChildByType(DlangTypes.KW_EXTERN);
    }

    @Nullable
    public PsiElement getOP_COMMA() {
        return findChildByType(DlangTypes.OP_COMMA);
    }

}
