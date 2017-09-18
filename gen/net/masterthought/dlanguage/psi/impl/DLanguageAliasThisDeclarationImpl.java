package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAliasThisDeclaration;
import net.masterthought.dlanguage.psi.DlangIdentifier;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.*;


public class DLanguageAliasThisDeclarationImpl extends ASTWrapperPsiElement implements DLanguageAliasThisDeclaration {
    public DLanguageAliasThisDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAliasThisDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getKW_ALIAS() {
        return findChildByType(KW_ALIAS);
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    public PsiElement getKW_THIS() {
        return findChildByType(KW_THIS);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }

}
