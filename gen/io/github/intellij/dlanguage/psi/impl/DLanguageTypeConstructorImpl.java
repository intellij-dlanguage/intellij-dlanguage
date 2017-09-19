package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.DLanguageTypeConstructor;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageTypeConstructorImpl extends ASTWrapperPsiElement implements DLanguageTypeConstructor {
    public DLanguageTypeConstructorImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTypeConstructor(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getKW_CONST() {
        return findChildByType(DlangTypes.KW_CONST);
    }

    @Nullable
    public PsiElement getKW_IMMUTABLE() {
        return findChildByType(DlangTypes.KW_IMMUTABLE);
    }

    @Nullable
    public PsiElement getKW_INOUT() {
        return findChildByType(DlangTypes.KW_INOUT);
    }

    @Nullable
    public PsiElement getKW_SHARED() {
        return findChildByType(DlangTypes.KW_SHARED);
    }

    @Nullable
    public PsiElement getKW_SCOPE() {
        return findChildByType(DlangTypes.KW_SCOPE);
    }

}
