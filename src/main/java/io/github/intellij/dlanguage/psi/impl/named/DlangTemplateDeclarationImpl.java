package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import io.github.intellij.dlanguage.stubs.DlangTemplateDeclarationStub;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import io.github.intellij.dlanguage.stubs.DlangTemplateDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_TEMPLATE;

public class DlangTemplateDeclarationImpl extends DNamedStubbedPsiElementBase<DlangTemplateDeclarationStub> implements DlangTemplateDeclaration {

    public DlangTemplateDeclarationImpl(final DlangTemplateDeclarationStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DlangTemplateDeclarationImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitTemplateDeclaration(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public DLanguageConstraint getConstraint() {
        return PsiTreeUtil.getChildOfType(this, DLanguageConstraint.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_BRACES_RIGHT() {
        return null;
    }

    @Nullable
    @Override
    public PsiElement getOP_BRACES_LEFT() {
        return null;
    }

    @NotNull
    @Override
    public List<DLanguageDeclaration> getDeclarations() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageDeclaration.class);
    }

    @Nullable
    @Override
    public DLanguageEponymousTemplateDeclaration getEponymousTemplateDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageEponymousTemplateDeclaration.class);
    }

    @Nullable
    @Override
    public PsiElement getKW_TEMPLATE() {
        return findChildByType(DlangTypes.KW_TEMPLATE);
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
    public DlangIdentifier getNameIdentifier() {
        return getIdentifier();
    }

    public boolean processDeclarations(@NotNull final PsiScopeProcessor processor, @NotNull final ResolveState state, final PsiElement lastParent, @NotNull final PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }

}
