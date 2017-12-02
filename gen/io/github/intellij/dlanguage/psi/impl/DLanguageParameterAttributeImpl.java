package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_AUTO;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_FINAL;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_IN;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_LAZY;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_OUT;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_REF;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_SCOPE;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageParameterAttribute;
import io.github.intellij.dlanguage.psi.DLanguageTypeConstructor;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageParameterAttributeImpl extends ASTWrapperPsiElement implements
    DLanguageParameterAttribute {

    public DLanguageParameterAttributeImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitParameterAttribute(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getKW_FINAL() {
        return findChildByType(KW_FINAL);
    }

    @Nullable
    public PsiElement getKW_IN() {
        return findChildByType(KW_IN);
    }

    @Nullable
    public PsiElement getKW_LAZY() {
        return findChildByType(KW_LAZY);
    }

    @Nullable
    public PsiElement getKW_OUT() {
        return findChildByType(KW_OUT);
    }

    @Nullable
    public PsiElement getKW_REF() {
        return findChildByType(KW_REF);
    }

    @Nullable
    public PsiElement getKW_SCOPE() {
        return findChildByType(KW_SCOPE);
    }

    @Nullable
    public PsiElement getKW_AUTO() {
        return findChildByType(KW_AUTO);
    }

    @Nullable
    public DLanguageTypeConstructor getTypeConstructor() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTypeConstructor.class);
    }
}
