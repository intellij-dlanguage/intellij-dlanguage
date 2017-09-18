package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguagePragmaDeclaration;
import net.masterthought.dlanguage.psi.DLanguagePragmaExpression;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_SCOLON;


public class DLanguagePragmaDeclarationImpl extends ASTWrapperPsiElement implements DLanguagePragmaDeclaration {
    public DLanguagePragmaDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitPragmaDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguagePragmaExpression getPragmaExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguagePragmaExpression.class);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }

}
