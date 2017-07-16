package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.icons.DLanguageIcons;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;
import net.masterthought.dlanguage.psi.references.DReference;
import net.masterthought.dlanguage.stubs.DLanguageParameterStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

public class DLanguageParameterImpl extends DNamedStubbedPsiElementBase<DLanguageParameterStub> implements DLanguageParameter {

    public DLanguageParameterImpl(DLanguageParameterStub stub, IStubElementType type) {
        super(stub, type);
    }

    public DLanguageParameterImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitParameter(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public DLanguageAssignExpression getAssignExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_EQ() {
        return findChildByType(OP_EQ);
    }

    @Override
    @Nullable
    public DLanguageIdentifier getIdentifier() {
        return PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class);
    }

    @NotNull
    @Override
    public List<DLanguageTypeSuffix> getTypeSuffixs() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this,DLanguageTypeSuffix.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_TRIPLEDOT() {
        return null;
    }

    @NotNull
    @Override
    public List<DLanguageParameterAttribute> getParameterAttributes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this,DLanguageParameterAttribute.class);
    }

    @Override
    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @NotNull
    public String getName() {
        return getIdentifier().getName();
    }

//    public String getFullName() {
//        return DPsiImplUtil.getFullName(this);
//    }

    @Nullable
    public PsiElement getNameIdentifier() {
        ASTNode keyNode = getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public PsiReference getReference() {
        return new DReference(this, TextRange.from(0, (this).getName().length()));
    }

    @NotNull
    public PsiElement setName(String newName) {
        if (getIdentifier() == null) {
            throw new IllegalStateException("cannot rename");
        }

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

    public boolean isSomeVisibility(Visibility visibility) {
        //todo fix
        return false;
    }

}
