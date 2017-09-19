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

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageStorageClassImpl extends ASTWrapperPsiElement implements DLanguageStorageClass {
    public DLanguageStorageClassImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitStorageClass(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageAtAttribute getAtAttribute() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAtAttribute.class);
    }

    @Nullable
    public DLanguageDeprecated getDeprecated() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDeprecated.class);
    }

    @Nullable
    public DLanguageAlignAttribute getAlignAttribute() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAlignAttribute.class);
    }

    @Nullable
    public DLanguageLinkageAttribute getLinkageAttribute() {
        return PsiTreeUtil.getChildOfType(this, DLanguageLinkageAttribute.class);
    }

    @Nullable
    public PsiElement getKW_SYNCHRONIZED() {
        return findChildByType(DlangTypes.KW_SYNCHRONIZED);
    }

    @Nullable
    public DLanguageTypeConstructor getTypeConstructor() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTypeConstructor.class);
    }

    @Nullable
    public PsiElement getKW_ABSTRACT() {
        return findChildByType(DlangTypes.KW_ABSTRACT);
    }

    @Nullable
    public PsiElement getKW_AUTO() {
        return findChildByType(DlangTypes.KW_AUTO);
    }

    @Nullable
    public PsiElement getKW_ENUM() {
        return findChildByType(DlangTypes.KW_ENUM);
    }

    @Nullable
    public PsiElement getKW_EXTERN() {
        return findChildByType(DlangTypes.KW_EXTERN);
    }

    @Nullable
    public PsiElement getKW_FINAL() {
        return findChildByType(DlangTypes.KW_FINAL);
    }

    @Nullable
    public PsiElement getKW_INOUT() {
        return findChildByType(DlangTypes.KW_INOUT);
    }

    @Nullable
    public PsiElement getKW_NOTHROW() {
        return findChildByType(DlangTypes.KW_NOTHROW);
    }

    @Nullable
    public PsiElement getKW_OVERRIDE() {
        return findChildByType(DlangTypes.KW_OVERRIDE);
    }

    @Nullable
    public PsiElement getKW_PURE() {
        return findChildByType(DlangTypes.KW_PURE);
    }

    @Nullable
    public PsiElement getKW_REF() {
        return findChildByType(DlangTypes.KW_REF);
    }

    @Nullable
    public PsiElement getKW___GSHARED() {
        return findChildByType(DlangTypes.KW___GSHARED);
    }

    @Nullable
    public PsiElement getKW_SCOPE() {
        return findChildByType(DlangTypes.KW_SCOPE);
    }

    @Nullable
    public PsiElement getKW_STATIC() {
        return findChildByType(DlangTypes.KW_STATIC);
    }

}
