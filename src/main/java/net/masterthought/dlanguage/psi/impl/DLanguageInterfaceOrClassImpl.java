package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.interfaces.CanInherit;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import net.masterthought.dlanguage.psi.references.DReference;
import net.masterthought.dlanguage.stubs.DLanguageInterfaceOrClassDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;
import java.util.Map;

import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_COLON;

public class DLanguageInterfaceOrClassImpl extends DNamedStubbedPsiElementBase<DLanguageInterfaceOrClassDeclarationStub> implements DLanguageInterfaceOrClass {

    public DLanguageInterfaceOrClassImpl(DLanguageInterfaceOrClassDeclarationStub stub, IStubElementType type) {
        super(stub, type);
    }

    public DLanguageInterfaceOrClassImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitClassDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
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
        return notNullChild(PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class));
    }

    @Override
    @Nullable
    public DLanguageTemplateParameters getTemplateParameters() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_COLON() {
        return notNullChild(findChildByType(OP_COLON));
    }

    @Nullable
    @Override
    public DLanguageStructBody getStructBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStructBody.class);
    }

    @NotNull
    public String getName() {
        return DPsiImplUtil.getName(this);
    }

    public String getFullName() {
        return DPsiImplUtil.getFullName(this);
    }

    @Nullable
    public PsiElement getNameIdentifier() {
        ASTNode keyNode = getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public PsiReference getReference() {
        return new DReference(this, TextRange.from(0, DPsiImplUtil.getName(this).length()));
    }

    @NotNull
    public PsiElement setName(String newName) {
        getIdentifier().setName(newName);
        return this;
    }

    @NotNull
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @NotNull
            @Override
            public String getPresentableText() {
                return getName();
            }

            /**
             * This is needed to decipher between files when resolving multiple references.
             */
            @Nullable
            @Override
            public String getLocationString() {
                final PsiFile psiFile = getContainingFile();
                return psiFile instanceof DLanguageFile ? ((DLanguageFile) psiFile).getModuleOrFileName() : null;
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return DLanguageIcons.FILE;
            }
        };
    }

    public boolean isSomeVisibility(HasVisibility.Visibility visibility, Class<? extends Container> containerType) {
        //todo fix
        return false;
    }

    public boolean isSomeVisibility(HasVisibility.Visibility visibility) {
        //todo fix
        return false;
    }

    public List<CanInherit> whatInheritsFrom() {
        return DPsiImplUtil.whatInheritsFrom(this);
    }

    public Map<String, DLanguageIdentifier> getSuperClassNames() {
        return DPsiImplUtil.getSuperClassNames(this);
    }

    public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
        return DPsiImplUtil.processDeclarations(this, processor, state, lastParent, place);
    }

}
