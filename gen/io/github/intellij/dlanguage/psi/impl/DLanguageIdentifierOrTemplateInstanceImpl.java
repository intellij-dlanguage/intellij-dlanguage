package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageIdentifierOrTemplateInstance;
import io.github.intellij.dlanguage.psi.DLanguageTemplateInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageIdentifierOrTemplateInstanceImpl extends ASTWrapperPsiElement implements
    DLanguageIdentifierOrTemplateInstance {

    public DLanguageIdentifierOrTemplateInstanceImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitIdentifierOrTemplateInstance(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) {
            accept((DLanguageVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
    }

    @Nullable
    public DLanguageTemplateInstance getTemplateInstance() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTemplateInstance.class);
    }
}
