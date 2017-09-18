package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageParameterAttribute;
import net.masterthought.dlanguage.psi.DLanguageTypeConstructor;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.*;


public class DLanguageParameterAttributeImpl extends ASTWrapperPsiElement implements DLanguageParameterAttribute {
    public DLanguageParameterAttributeImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitParameterAttribute(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
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
