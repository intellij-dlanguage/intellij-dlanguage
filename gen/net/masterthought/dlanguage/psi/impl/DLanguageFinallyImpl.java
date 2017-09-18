package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageDeclarationOrStatement;
import net.masterthought.dlanguage.psi.DLanguageFinally;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.KW_FINALLY;


public class DLanguageFinallyImpl extends ASTWrapperPsiElement implements DLanguageFinally {
    public DLanguageFinallyImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitFinally(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getKW_FINALLY() {
        return findChildByType(KW_FINALLY);
    }

    @Nullable
    public DLanguageDeclarationOrStatement getDeclarationOrStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDeclarationOrStatement.class);
    }
}
