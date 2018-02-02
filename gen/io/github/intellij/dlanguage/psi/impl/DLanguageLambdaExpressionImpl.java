package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_DELEGATE;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_FUNCTION;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_LAMBDA_ARROW;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAssignExpression;
import io.github.intellij.dlanguage.psi.DLanguageFunctionAttribute;
import io.github.intellij.dlanguage.psi.DLanguageLambdaExpression;
import io.github.intellij.dlanguage.psi.DLanguageParameters;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageLambdaExpressionImpl extends ASTWrapperPsiElement implements
    DLanguageLambdaExpression {

    public DLanguageLambdaExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitLambdaExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
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
    public PsiElement getOP_LAMBDA_ARROW() {
        return findChildByType(OP_LAMBDA_ARROW);
    }

    @Nullable
    public DLanguageAssignExpression getAssignExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
    }

    @Nullable
    public DLanguageParameters getParameters() {
        return PsiTreeUtil.getChildOfType(this, DLanguageParameters.class);
    }

    @NotNull
    public List<DLanguageFunctionAttribute> getFunctionAttributes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageFunctionAttribute.class);
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
