package net.masterthought.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import net.masterthought.dlanguage.resolve.ScopeProcessorImpl;
import net.masterthought.dlanguage.stubs.DlangEponymousTemplateDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.*;

public class DLanguageEponymousTemplateDeclarationImpl extends DNamedStubbedPsiElementBase<DlangEponymousTemplateDeclarationStub> implements DLanguageEponymousTemplateDeclaration {

    public DLanguageEponymousTemplateDeclarationImpl(final DlangEponymousTemplateDeclarationStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DLanguageEponymousTemplateDeclarationImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitEponymousTemplateDeclaration(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }


    @Override
    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getStubChildOfType(this, DlangIdentifier.class);
    }


    @Override
    @Nullable
    public DLanguageTemplateParameters getTemplateParameters() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_EQ() {
        return findChildByType(OP_EQ);
    }

    @Override
    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }

    @Nullable
    @Override
    public PsiElement getKW_ENUM() {
        return findChildByType(KW_ENUM);
    }

    @Nullable
    @Override
    public PsiElement getKW_ALIAS() {
        return findChildByType(KW_ALIAS);
    }

    @Nullable
    public DlangIdentifier getNameIdentifier() {
        return getIdentifier();
    }

    public boolean processDeclarations(@NotNull final PsiScopeProcessor processor, @NotNull final ResolveState state, final PsiElement lastParent, @NotNull final PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }

}
