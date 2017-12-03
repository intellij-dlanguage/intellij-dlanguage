package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_ABSTRACT;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_AUTO;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_CONST;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_ENUM;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_EXTERN;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_FINAL;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_IMMUTABLE;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_INOUT;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_NOTHROW;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_OVERRIDE;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_PURE;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_REF;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_SCOPE;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_STATIC;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_SYNCHRONIZED;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___GSHARED;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAlignAttribute;
import io.github.intellij.dlanguage.psi.DLanguageAtAttribute;
import io.github.intellij.dlanguage.psi.DLanguageDeprecated;
import io.github.intellij.dlanguage.psi.DLanguageLinkageAttribute;
import io.github.intellij.dlanguage.psi.DLanguageStorageClass;
import io.github.intellij.dlanguage.psi.DLanguageTypeConstructor;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageStorageClassImpl extends ASTWrapperPsiElement implements
    DLanguageStorageClass {

    public DLanguageStorageClassImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitStorageClass(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
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
        return findChildByType(KW_SYNCHRONIZED);
    }

    @Nullable
    public DLanguageTypeConstructor getTypeConstructor() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTypeConstructor.class);
    }

    @Nullable
    public PsiElement getKW_ABSTRACT() {
        return findChildByType(KW_ABSTRACT);
    }

    @Nullable
    public PsiElement getKW_CONST() {
        return findChildByType(KW_CONST);
    }

    @Nullable
    public PsiElement getKW_IMMUTABLE() {
        return findChildByType(KW_IMMUTABLE);
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

}
