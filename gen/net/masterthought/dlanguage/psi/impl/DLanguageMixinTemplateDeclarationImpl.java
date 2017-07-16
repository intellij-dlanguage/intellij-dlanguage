package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageMixinTemplateDeclaration;
import net.masterthought.dlanguage.psi.DLanguageTemplateDeclaration;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DLanguageTypes.KW_MIXIN;


public class DLanguageMixinTemplateDeclarationImpl extends ASTWrapperPsiElement implements DLanguageMixinTemplateDeclaration {
    public DLanguageMixinTemplateDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitMixinTemplateDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageTemplateDeclaration getTemplateDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTemplateDeclaration.class);
    }

    @Nullable
    public PsiElement getKW_MIXIN() {
        return findChildByType(KW_MIXIN);
    }

}
