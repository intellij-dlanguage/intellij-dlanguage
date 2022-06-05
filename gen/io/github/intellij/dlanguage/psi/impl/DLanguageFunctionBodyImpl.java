package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageFunctionBodyImpl extends ASTWrapperPsiElement implements
    DLanguageFunctionBody {

    public DLanguageFunctionBodyImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitFunctionBody(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageSpecifiedFunctionBody getSpecifiedFunctionBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageSpecifiedFunctionBody.class);
    }

    @Nullable
    public DLanguageMissingFunctionBody getMissingFunctionBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageMissingFunctionBody.class);
    }

    @Nullable
    public DLanguageShortenedFunctionBody getShortenedFunctionBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageShortenedFunctionBody.class);
    }
}
