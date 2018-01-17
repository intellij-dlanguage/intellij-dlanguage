package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_MIXIN;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageTemplateMixinDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangTemplateDeclaration;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageTemplateMixinDeclarationImpl extends ASTWrapperPsiElement implements
    DLanguageTemplateMixinDeclaration {

    public DLanguageTemplateMixinDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTemplateMixinDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getKW_MIXIN() {
        return findChildByType(KW_MIXIN);
    }

    @Nullable
    public DlangTemplateDeclaration getTemplateDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DlangTemplateDeclaration.class);
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor,
        @NotNull ResolveState state,
        PsiElement lastParent,
        @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE
            .processDeclarations(this, processor, state, lastParent, place);
    }
}
