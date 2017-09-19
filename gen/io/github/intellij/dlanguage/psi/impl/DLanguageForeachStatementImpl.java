package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageForeachStatementImpl extends ASTWrapperPsiElement implements DLanguageForeachStatement {
    public DLanguageForeachStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitForeachStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getKW_FOREACH() {
        return findChildByType(DlangTypes.KW_FOREACH);
    }

    @Nullable
    public PsiElement getKW_FOREACH_REVERSE() {
        return findChildByType(DlangTypes.KW_FOREACH_REVERSE);
    }

    @Nullable
    public DLanguageDeclarationOrStatement getDeclarationOrStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDeclarationOrStatement.class);
    }

    @NotNull
    public List<DLanguageExpression> getExpressions() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageExpression.class);
    }

    @Nullable
    public PsiElement getOP_BRACES_RIGHT() {
        return findChildByType(DlangTypes.OP_BRACES_RIGHT);
    }

    @Nullable
    public PsiElement getOP_BRACES_LEFT() {
        return findChildByType(DlangTypes.OP_BRACES_LEFT);
    }

    @Nullable
    public PsiElement getOP_DDOT() {
        return findChildByType(DlangTypes.OP_DDOT);
    }

    @Nullable
    public DLanguageForeachType getForeachType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageForeachType.class);
    }

    @Nullable
    public DLanguageForeachTypeList getForeachTypeList() {
        return PsiTreeUtil.getChildOfType(this, DLanguageForeachTypeList.class);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(DlangTypes.OP_SCOLON);
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor,
                                       @NotNull ResolveState state,
                                       PsiElement lastParent,
                                       @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }
}
