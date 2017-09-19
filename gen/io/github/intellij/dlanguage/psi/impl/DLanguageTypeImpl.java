package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class DLanguageTypeImpl extends ASTWrapperPsiElement implements DLanguageType {
    public DLanguageTypeImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitType(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageAttribute getAttribute() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAttribute.class);
    }

    @Nullable
    public DLanguageType_2 getType_2() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType_2.class);
    }

    @NotNull
    public List<DLanguageTypeSuffix> getTypeSuffixs() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageTypeSuffix.class);
    }
}
