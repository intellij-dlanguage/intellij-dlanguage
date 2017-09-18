package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageCompileCondition;
import net.masterthought.dlanguage.psi.DLanguageConditionalDeclaration;
import net.masterthought.dlanguage.psi.DLanguageDeclaration;
import net.masterthought.dlanguage.psi.DlangVisitor;
import net.masterthought.dlanguage.resolve.ScopeProcessorImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.masterthought.dlanguage.psi.DlangTypes.*;


public class DLanguageConditionalDeclarationImpl extends ASTWrapperPsiElement implements DLanguageConditionalDeclaration {
    public DLanguageConditionalDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitConditionalDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageCompileCondition getCompileCondition() {
        return PsiTreeUtil.getChildOfType(this, DLanguageCompileCondition.class);
    }

    @NotNull
    public List<DLanguageDeclaration> getDeclarations() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageDeclaration.class);
    }

    @Nullable
    public PsiElement getOP_COLON() {
        return findChildByType(OP_COLON);
    }

    @Nullable
    public PsiElement getKW_ELSE() {
        return findChildByType(KW_ELSE);
    }

    @Nullable
    public PsiElement getOP_BRACES_RIGHT() {
        return findChildByType(OP_BRACES_RIGHT);
    }

    @Nullable
    public PsiElement getOP_BRACES_LEFT() {
        return findChildByType(OP_BRACES_LEFT);
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor,
                                       @NotNull ResolveState state,
                                       PsiElement lastParent,
                                       @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }
}
