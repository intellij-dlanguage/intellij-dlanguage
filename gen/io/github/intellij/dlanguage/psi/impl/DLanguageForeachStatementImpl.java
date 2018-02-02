package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_FOREACH;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_FOREACH_REVERSE;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACES_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACES_RIGHT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_DDOT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_SCOLON;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageDeclarationOrStatement;
import io.github.intellij.dlanguage.psi.DLanguageExpression;
import io.github.intellij.dlanguage.psi.DLanguageForeachStatement;
import io.github.intellij.dlanguage.psi.DLanguageForeachTypeList;
import io.github.intellij.dlanguage.psi.named.DlangForeachType;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageForeachStatementImpl extends ASTWrapperPsiElement implements
    DLanguageForeachStatement {

    public DLanguageForeachStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitForeachStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getKW_FOREACH() {
        return findChildByType(KW_FOREACH);
    }

    @Nullable
    public PsiElement getKW_FOREACH_REVERSE() {
        return findChildByType(KW_FOREACH_REVERSE);
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
        return findChildByType(OP_BRACES_RIGHT);
    }

    @Nullable
    public PsiElement getOP_BRACES_LEFT() {
        return findChildByType(OP_BRACES_LEFT);
    }

    @Nullable
    public PsiElement getOP_DDOT() {
        return findChildByType(OP_DDOT);
    }

    @Nullable
    public DlangForeachType getForeachType() {
        return PsiTreeUtil.getChildOfType(this, DlangForeachType.class);
    }

    @Nullable
    public DLanguageForeachTypeList getForeachTypeList() {
        return PsiTreeUtil.getChildOfType(this, DLanguageForeachTypeList.class);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
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
