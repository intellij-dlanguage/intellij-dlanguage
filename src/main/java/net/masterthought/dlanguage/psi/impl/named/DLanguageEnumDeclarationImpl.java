package net.masterthought.dlanguage.psi.impl.named;

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
import net.masterthought.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility;
import net.masterthought.dlanguage.psi.references.DReference;
import net.masterthought.dlanguage.stubs.DLanguageEnumDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import static net.masterthought.dlanguage.psi.DLanguageTypes.KW_ENUM;
import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_COLON;

public class DLanguageEnumDeclarationImpl extends DNamedStubbedPsiElementBase<DLanguageEnumDeclarationStub> implements DLanguageEnumDeclaration {

    public DLanguageEnumDeclarationImpl(DLanguageEnumDeclarationStub stub, IStubElementType type) {
        super(stub, type);
    }

    public DLanguageEnumDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitEnumDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public DLanguageEnumBody getEnumBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageEnumBody.class);
    }

    @Override
    @Nullable
    public DLanguageIdentifier getIdentifier() {
        return PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_COLON() {
        return findChildByType(OP_COLON);
    }

    @Nullable
    @Override
    public PsiElement getKW_ENUM() {
        return findChildByType(KW_ENUM);
    }

    @Nullable
    @Override
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @NotNull
    public String getName() {
        if (getStub() != null) {
            return getStub().getName();
        }
        return getIdentifier().getName();
    }

    @Nullable
    public PsiElement getNameIdentifier() {
        ASTNode keyNode = getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }

    @NotNull
    public PsiReference getReference() {
        return new DReference(this, TextRange.from(0, (this).getName().length()));
    }

    @Nullable
    public PsiElement setName(String newName) {
        return getIdentifier().setName(newName);
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

    public boolean isSomeVisibility(HasVisibility.Visibility visibility) {
        //todo fix
        return false;

    }

}
