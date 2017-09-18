package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageStaticAssertDeclaration;
import net.masterthought.dlanguage.psi.DLanguageStaticAssertStatement;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageStaticAssertDeclarationImpl extends ASTWrapperPsiElement implements DLanguageStaticAssertDeclaration {
    public DLanguageStaticAssertDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitStaticAssertDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageStaticAssertStatement getStaticAssertStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStaticAssertStatement.class);
    }
}
