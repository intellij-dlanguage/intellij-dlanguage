package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_CLASS;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_CONST;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_DELEGATE;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_ENUM;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_FUNCTION;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_IMMUTABLE;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_INOUT;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_INTERFACE;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_RETURN;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_SHARED;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_STRUCT;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_SUPER;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_UNION;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___PARAMETERS;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.DLanguageTypeSpecialization;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageTypeSpecializationImpl extends ASTWrapperPsiElement implements
    DLanguageTypeSpecialization {

    public DLanguageTypeSpecializationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTypeSpecialization(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    public PsiElement getKW___PARAMETERS() {
        return findChildByType(KW___PARAMETERS);
    }

    @Nullable
    public PsiElement getKW_STRUCT() {
        return findChildByType(KW_STRUCT);
    }

    @Nullable
    public PsiElement getKW_UNION() {
        return findChildByType(KW_UNION);
    }

    @Nullable
    public PsiElement getKW_CLASS() {
        return findChildByType(KW_CLASS);
    }

    @Nullable
    public PsiElement getKW_INTERFACE() {
        return findChildByType(KW_INTERFACE);
    }

    @Nullable
    public PsiElement getKW_ENUM() {
        return findChildByType(KW_ENUM);
    }

    @Nullable
    public PsiElement getKW_FUNCTION() {
        return findChildByType(KW_FUNCTION);
    }

    @Nullable
    public PsiElement getKW_DELEGATE() {
        return findChildByType(KW_DELEGATE);
    }

    @Nullable
    public PsiElement getKW_SUPER() {
        return findChildByType(KW_SUPER);
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
    public PsiElement getKW_INOUT() {
        return findChildByType(KW_INOUT);
    }

    @Nullable
    public PsiElement getKW_SHARED() {
        return findChildByType(KW_SHARED);
    }

    @Nullable
    public PsiElement getKW_RETURN() {
        return findChildByType(KW_RETURN);
    }

}
