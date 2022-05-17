package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.named.DlangForeachType;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;

public class DLanguageStaticForeachDeclarationImpl extends ASTWrapperPsiElement implements
    DLanguageStaticForeachDeclaration {

    public DLanguageStaticForeachDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitStaticForeachDeclaration(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Override
    public @Nullable PsiElement getKW_FOREACH() {
        return findChildByType(KW_FOREACH);
    }

    @Override
    public @Nullable PsiElement getKW_FOREACH_REVERSE() {
        return findChildByType(KW_FOREACH_REVERSE);
    }

    @Override
    public @Nullable DLanguageDeclaration getDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDeclaration.class);
    }

    @Override
    public @NotNull List<DLanguageExpression> getExpressions() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageExpression.class);
    }

    @Override
    public @Nullable PsiElement getOP_BRACES_RIGHT() {
        return findChildByType(OP_BRACES_RIGHT);
    }

    @Override
    public @Nullable PsiElement getOP_BRACES_LEFT() {
        return findChildByType(OP_BRACES_LEFT);
    }

    @Override
    public @Nullable PsiElement getOP_DDOT() {
        return findChildByType(OP_DDOT);
    }

    @Override
    public @Nullable DlangForeachType getForeachType() {
        return PsiTreeUtil.getChildOfType(this, DlangForeachType.class);
    }

    @Override
    public @Nullable DLanguageForeachTypeList getForeachTypeList() {
        return PsiTreeUtil.getChildOfType(this, DLanguageForeachTypeList.class);
    }

    @Override
    public @Nullable PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }

    @Override
    public @NotNull PsiElement getKW_STATIC() {
        return findChildByType(KW_STATIC);
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
