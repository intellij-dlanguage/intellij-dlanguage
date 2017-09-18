package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageDeclaration;
import net.masterthought.dlanguage.psi.DLanguageDeclarationOrStatement;
import net.masterthought.dlanguage.psi.DLanguageStatement;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageDeclarationOrStatementImpl extends ASTWrapperPsiElement implements DLanguageDeclarationOrStatement {
    public DLanguageDeclarationOrStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitDeclarationOrStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageStatement getStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStatement.class);
    }

    @Nullable
    public DLanguageDeclaration getDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDeclaration.class);
    }
}
