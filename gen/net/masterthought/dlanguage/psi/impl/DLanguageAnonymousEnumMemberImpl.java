package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import net.masterthought.dlanguage.psi.DLanguageAnonymousEnumMember;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;


public class DLanguageAnonymousEnumMemberImpl extends ASTWrapperPsiElement implements DLanguageAnonymousEnumMember {
    public DLanguageAnonymousEnumMemberImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAnonymousEnumMember(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

}
