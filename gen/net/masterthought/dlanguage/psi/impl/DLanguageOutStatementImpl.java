package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageBlockStatement;
import net.masterthought.dlanguage.psi.DlangIdentifier;
import net.masterthought.dlanguage.psi.DLanguageOutStatement;
import net.masterthought.dlanguage.psi.DlangVisitor;
import net.masterthought.dlanguage.resolve.ScopeProcessorImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.*;


public class DLanguageOutStatementImpl extends ASTWrapperPsiElement implements DLanguageOutStatement {
    public DLanguageOutStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitOutStatement(this);
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
    public DLanguageBlockStatement getBlockStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageBlockStatement.class);
    }

    @Nullable
    public PsiElement getKW_OUT() {
        return findChildByType(KW_OUT);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(OP_PAR_LEFT);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(OP_PAR_RIGHT);
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor,
                                       @NotNull ResolveState state,
                                       PsiElement lastParent,
                                       @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }
}
