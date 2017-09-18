package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DlangIdentifier;
import net.masterthought.dlanguage.psi.DLanguageIdentifierOrTemplateInstance;
import net.masterthought.dlanguage.psi.DLanguageTemplateInstance;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageIdentifierOrTemplateInstanceImpl extends ASTWrapperPsiElement implements DLanguageIdentifierOrTemplateInstance {
    public DLanguageIdentifierOrTemplateInstanceImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitIdentifierOrTemplateInstance(this);
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
    public DLanguageTemplateInstance getTemplateInstance() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTemplateInstance.class);
    }
}
