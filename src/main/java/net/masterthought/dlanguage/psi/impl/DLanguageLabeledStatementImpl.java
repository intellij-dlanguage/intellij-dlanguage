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
import net.masterthought.dlanguage.psi.references.DReference;
import net.masterthought.dlanguage.stubs.DLanguageLabeledStatementStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_COLON;

public class DLanguageLabeledStatementImpl extends DNamedStubbedPsiElementBase<DLanguageLabeledStatementStub> implements DLanguageLabeledStatement {

    public DLanguageLabeledStatementImpl(DLanguageLabeledStatementStub stub, IStubElementType type) {
        super(stub, type);
    }

    public DLanguageLabeledStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitLabeledStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public DLanguageIdentifier getIdentifier() {
        return notNullChild(PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class));
    }

    @Nullable
    @Override
    public PsiElement getOP_COLON() {
        return notNullChild(findChildByType(OP_COLON));
    }

    @Nullable
    @Override
    public DLanguageDeclarationOrStatement getDeclarationOrStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDeclarationOrStatement.class);
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

    public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
        return DPsiImplUtil.processDeclarations(this, processor, state, lastParent, place);
    }

}
