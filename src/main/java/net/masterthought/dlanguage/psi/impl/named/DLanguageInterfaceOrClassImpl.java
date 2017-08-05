package net.masterthought.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import net.masterthought.dlanguage.psi.references.DReference;
import net.masterthought.dlanguage.resolve.ScopeProcessorImpl;
import net.masterthought.dlanguage.stubs.DLanguageInterfaceOrClassStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_COLON;

public class DLanguageInterfaceOrClassImpl extends DNamedStubbedPsiElementBase<DLanguageInterfaceOrClassStub> implements DLanguageInterfaceOrClass {

    public DLanguageInterfaceOrClassImpl(final DLanguageInterfaceOrClassStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DLanguageInterfaceOrClassImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DLanguageVisitor visitor) {
        visitor.visitInterfaceOrClass(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public DLanguageBaseClassList getBaseClassList() {
        return PsiTreeUtil.getChildOfType(this, DLanguageBaseClassList.class);
    }

    @Override
    @Nullable
    public DLanguageConstraint getConstraint() {
        return PsiTreeUtil.getChildOfType(this, DLanguageConstraint.class);
    }

    @Override
    @NotNull
    public DLanguageIdentifier getIdentifier() {
        return PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class);
    }

    @Override
    @Nullable
    public DLanguageTemplateParameters getTemplateParameters() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_COLON() {
        return findChildByType(OP_COLON);
    }

    @Nullable
    @Override
    public DLanguageStructBody getStructBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStructBody.class);
    }
//    public String getFullName() {
//        return DPsiImplUtil.getFullName(this);
//    }

    @Nullable
    public DLanguageIdentifier getNameIdentifier() {
        return getIdentifier();
    }

    @NotNull
    public PsiReference getReference() {
        return new DReference(this, TextRange.from(0, (this).getName().length()));
    }

    @NotNull
    public PsiElement setName(@NotNull final String newName) {
        getIdentifier().setName(newName);
        return this;
    }


    public boolean processDeclarations(@NotNull final PsiScopeProcessor processor, @NotNull final ResolveState state, final PsiElement lastParent, @NotNull final PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }

}
