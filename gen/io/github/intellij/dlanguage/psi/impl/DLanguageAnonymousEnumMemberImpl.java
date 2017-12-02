package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import io.github.intellij.dlanguage.psi.DLanguageAnonymousEnumMember;
import org.jetbrains.annotations.NotNull;


public class DLanguageAnonymousEnumMemberImpl extends ASTWrapperPsiElement implements
    DLanguageAnonymousEnumMember {

    public DLanguageAnonymousEnumMemberImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitAnonymousEnumMember(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) {
            accept((DLanguageVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

}
