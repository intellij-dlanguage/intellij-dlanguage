package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.psi.named.DLanguageInterfaceDeclaration;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import io.github.intellij.dlanguage.stubs.DLanguageInterfaceDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;

public class DlangInterfaceDeclarationImpl extends
    DNamedStubbedPsiElementBase<DLanguageInterfaceDeclarationStub> implements
    DLanguageInterfaceDeclaration {

    public DlangInterfaceDeclarationImpl(final DLanguageInterfaceDeclarationStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DlangInterfaceDeclarationImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitDNamedElement(this);
        visitor.visitInterfaceDeclaration(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    @Override
    public PsiElement getKW_INTERFACE() {
        return findChildByType(KW_INTERFACE);
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
    @Nullable
    public PsiElement getIdentifier() {
        return findChildByType(ID);
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
    public PsiElement getNameIdentifier() {
        return getIdentifier();
    }

    public boolean processDeclarations(@NotNull final PsiScopeProcessor processor, @NotNull final ResolveState state, final PsiElement lastParent, @NotNull final PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }

}
