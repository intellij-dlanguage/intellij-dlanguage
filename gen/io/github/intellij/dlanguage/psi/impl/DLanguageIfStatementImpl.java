package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageIfCondition;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DLanguageDeclarationOrStatement;
import io.github.intellij.dlanguage.psi.DLanguageIfCondition;
import io.github.intellij.dlanguage.psi.DLanguageIfStatement;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageIfStatementImpl extends ASTWrapperPsiElement implements DLanguageIfStatement {
    public DLanguageIfStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitIfStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public List<DLanguageDeclarationOrStatement> getDeclarationOrStatements() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageDeclarationOrStatement.class);
    }

    @Nullable
    public PsiElement getKW_ELSE() {
        return findChildByType(DlangTypes.KW_ELSE);
    }

    @Nullable
    public PsiElement getKW_IF() {
        return findChildByType(DlangTypes.KW_IF);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(DlangTypes.OP_PAR_LEFT);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(DlangTypes.OP_PAR_RIGHT);
    }

    @Nullable
    public DLanguageIfCondition getIfCondition() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIfCondition.class);
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor,
                                       @NotNull ResolveState state,
                                       PsiElement lastParent,
                                       @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }
}
