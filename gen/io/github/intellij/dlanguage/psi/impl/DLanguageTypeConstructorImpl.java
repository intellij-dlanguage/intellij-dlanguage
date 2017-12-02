package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_CONST;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_IMMUTABLE;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_INOUT;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_SCOPE;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_SHARED;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import io.github.intellij.dlanguage.psi.DLanguageTypeConstructor;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageTypeConstructorImpl extends ASTWrapperPsiElement implements
    DLanguageTypeConstructor {

    public DLanguageTypeConstructorImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTypeConstructor(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
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
    public PsiElement getKW_SCOPE() {
        return findChildByType(KW_SCOPE);
    }

}
