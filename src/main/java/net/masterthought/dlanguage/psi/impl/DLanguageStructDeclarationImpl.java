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
import net.masterthought.dlanguage.psi.interfaces.HasVisibility;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import net.masterthought.dlanguage.psi.references.DReference;
import net.masterthought.dlanguage.stubs.DLanguageStructDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import static net.masterthought.dlanguage.psi.DLanguageTypes.KW_STRUCT;
import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_SCOLON;

public class DLanguageStructDeclarationImpl extends DNamedStubbedPsiElementBase<DLanguageStructDeclarationStub> implements DLanguageStructDeclaration {

    public DLanguageStructDeclarationImpl(DLanguageStructDeclarationStub stub, IStubElementType type) {
        super(stub, type);
    }

    public DLanguageStructDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitStructDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public DLanguageConstraint getConstraint() {
        return PsiTreeUtil.getChildOfType(this, DLanguageConstraint.class);
    }

    @Nullable
    @Override
    public DLanguageStructBody getStructBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStructBody.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }

    @Nullable
    @Override
    public PsiElement getKW_STRUCT() {
        return notNullChild(findChildByType(KW_STRUCT));
    }

    @Override
    @Nullable
    public DLanguageIdentifier getIdentifier() {
        return PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class);
    }

    @Override
    @Nullable
    public DLanguageTemplateParameters getTemplateParameters() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class);
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

    @Nullable
    public PsiElement setName(String newName) {
        return DPsiImplUtil.setName(this, newName);
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

    public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
        return DPsiImplUtil.processDeclarations(this, processor, state, lastParent, place);
    }

}
