package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_DELEGATE;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_FUNCTION;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageFunctionAttribute;
import io.github.intellij.dlanguage.psi.DLanguageFunctionBody;
import io.github.intellij.dlanguage.psi.DLanguageFunctionLiteralExpression;
import io.github.intellij.dlanguage.psi.DLanguageParameters;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageFunctionLiteralExpressionImpl extends ASTWrapperPsiElement implements
    DLanguageFunctionLiteralExpression {

    public DLanguageFunctionLiteralExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitFunctionLiteralExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    public PsiElement getKW_FUNCTION() {
        return findChildByType(KW_FUNCTION);
    }

    @Nullable
    public PsiElement getKW_DELEGATE() {
        return findChildByType(KW_DELEGATE);
    }

    @Nullable
    public DLanguageParameters getParameters() {
        return PsiTreeUtil.getChildOfType(this, DLanguageParameters.class);
    }

    @NotNull
    public List<DLanguageFunctionAttribute> getFunctionAttributes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageFunctionAttribute.class);
    }

    @Nullable
    public DLanguageFunctionBody getFunctionBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageFunctionBody.class);
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
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
