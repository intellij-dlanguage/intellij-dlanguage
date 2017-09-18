package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DlangIdentifier;
import net.masterthought.dlanguage.psi.DLanguageNonVoidInitializer;
import net.masterthought.dlanguage.psi.DLanguageStructMemberInitializer;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_COLON;


public class DLanguageStructMemberInitializerImpl extends ASTWrapperPsiElement implements DLanguageStructMemberInitializer {
    public DLanguageStructMemberInitializerImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitStructMemberInitializer(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    public PsiElement getOP_COLON() {
        return findChildByType(OP_COLON);
    }

    @Nullable
    public DLanguageNonVoidInitializer getNonVoidInitializer() {
        return PsiTreeUtil.getChildOfType(this, DLanguageNonVoidInitializer.class);
    }
}
