package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.*;


public class DLanguageAttributeImpl extends ASTWrapperPsiElement implements DLanguageAttribute {
    public DLanguageAttributeImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitAttribute(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguagePragmaExpression getPragmaExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguagePragmaExpression.class);
    }

    @Nullable
    public PsiElement getKW_SYNCHRONIZED() {
        return findChildByType(KW_SYNCHRONIZED);
    }

    @Nullable
    public PsiElement getKW_ABSTRACT() {
        return findChildByType(KW_ABSTRACT);
    }

    @Nullable
    public PsiElement getKW_AUTO() {
        return findChildByType(KW_AUTO);
    }

    @Nullable
    public PsiElement getKW_ENUM() {
        return findChildByType(KW_ENUM);
    }

    @Nullable
    public PsiElement getKW_EXTERN() {
        return findChildByType(KW_EXTERN);
    }

    @Nullable
    public PsiElement getKW_FINAL() {
        return findChildByType(KW_FINAL);
    }

    @Nullable
    public PsiElement getKW_INOUT() {
        return findChildByType(KW_INOUT);
    }

    @Nullable
    public PsiElement getKW_NOTHROW() {
        return findChildByType(KW_NOTHROW);
    }

    @Nullable
    public PsiElement getKW_OVERRIDE() {
        return findChildByType(KW_OVERRIDE);
    }

    @Nullable
    public PsiElement getKW_PURE() {
        return findChildByType(KW_PURE);
    }

    @Nullable
    public PsiElement getKW_REF() {
        return findChildByType(KW_REF);
    }

    @Nullable
    public PsiElement getKW___GSHARED() {
        return findChildByType(KW___GSHARED);
    }

    @Nullable
    public PsiElement getKW_SCOPE() {
        return findChildByType(KW_SCOPE);
    }

    @Nullable
    public PsiElement getKW_STATIC() {
        return findChildByType(KW_STATIC);
    }

    @Nullable
    public PsiElement getKW_EXPORT() {
        return findChildByType(KW_EXPORT);
    }

    @Nullable
    public PsiElement getKW_PRIVATE() {
        return findChildByType(KW_PRIVATE);
    }

    @Nullable
    public PsiElement getKW_PROTECTED() {
        return findChildByType(KW_PROTECTED);
    }

    @Nullable
    public PsiElement getKW_PUBLIC() {
        return findChildByType(KW_PUBLIC);
    }

    @Nullable
    public DLanguageAlignAttribute getAlignAttribute() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAlignAttribute.class);
    }

    @Nullable
    public DLanguageDeprecated getDeprecated() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDeprecated.class);
    }

    @Nullable
    public DLanguageAtAttribute getAtAttribute() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAtAttribute.class);
    }

    @Nullable
    public PsiElement getKW_PACKAGE() {
        return findChildByType(KW_PACKAGE);
    }

    @Nullable
    public DLanguageIdentifierChain getIdentifierChain() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierChain.class);
    }

    @Nullable
    public DLanguageLinkageAttribute getLinkageAttribute() {
        return PsiTreeUtil.getChildOfType(this, DLanguageLinkageAttribute.class);
    }
}
