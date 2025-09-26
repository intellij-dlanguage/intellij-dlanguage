package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.progress.ProgressIndicatorProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.psi.named.DLanguageClassDeclaration;
import io.github.intellij.dlanguage.psi.named.DLanguageInterfaceDeclaration;
import io.github.intellij.dlanguage.psi.types.UserDefinedDType;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import io.github.intellij.dlanguage.stubs.DLanguageClassDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_CLASS;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_COLON;

public class DlangClassDeclarationImpl extends
    DNamedStubbedPsiElementBase<DLanguageClassDeclarationStub> implements
    DLanguageClassDeclaration {

    public DlangClassDeclarationImpl(final DLanguageClassDeclarationStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DlangClassDeclarationImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitDNamedElement(this);
        visitor.visitClassDeclaration(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    @Override
    public PsiElement getKW_CLASS() {
        return findChildByType(KW_CLASS);
    }

    @Override
    @Nullable
    public DLanguageBaseClassList getBaseClassList() {
        return PsiTreeUtil.getChildOfType(this, DLanguageBaseClassList.class);
    }

    @Override
    public @Nullable DLanguageClassDeclaration getParentClass() {
        // Object does not have a parent, it is the root of all the classes
        if ("Object".equals(getQualifiedName())) return null;

        var baseClassElement = getBaseClassList();
        if (baseClassElement == null || baseClassElement.getBasicTypes().isEmpty()) return null;

        var referenceElements = baseClassElement.getBasicTypes();
        for (var element : referenceElements) {
            ProgressIndicatorProvider.checkCanceled();
            var dtype = element.getDType();
            if (dtype instanceof UserDefinedDType elementType) {
                var psiResolved = elementType.resolve();
                if (psiResolved instanceof DLanguageClassDeclaration resolved) {
                    return resolved;
                }
            }
        }
        // TODO should actually return object class
        return null;
    }

    @Override
    public @NotNull List<DLanguageInterfaceDeclaration> getInterfaces() {
        var baseClassElement = getBaseClassList();
        if (baseClassElement == null || baseClassElement.getBasicTypes().isEmpty()) return List.of();

        var referenceElements = baseClassElement.getBasicTypes();
        var result = new ArrayList<DLanguageInterfaceDeclaration>();
        for (var element : referenceElements) {
            var dtype = element.getDType();
            if (dtype instanceof UserDefinedDType elementType) {
                var psiResolved = elementType.resolve();
                if (psiResolved instanceof DLanguageInterfaceDeclaration resolved) {
                    result.add(resolved);
                }
            }
        }
        return result;
    }

    @Override
    @Nullable
    public DLanguageConstraint getConstraint() {
        return PsiTreeUtil.getChildOfType(this, DLanguageConstraint.class);
    }

    @Override
    @Nullable
    public PsiElement getIdentifier() {
        return findChildByType(DlangTypes.ID);
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

    @Nullable
    public PsiElement getNameIdentifier() {
        return getIdentifier();
    }

    public boolean processDeclarations(@NotNull final PsiScopeProcessor processor, @NotNull final ResolveState state, final PsiElement lastParent, @NotNull final PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }

    @Nullable
    @Override
    public String getQualifiedName() {
        var file = getContainingFile();

        if (file instanceof DlangPsiFile dlangPsiFile)
            return dlangPsiFile.getFullyQualifiedModuleName() + "." + getName();

        return getName();
    }
}
