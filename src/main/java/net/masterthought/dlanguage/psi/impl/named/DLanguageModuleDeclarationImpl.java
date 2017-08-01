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
import net.masterthought.dlanguage.psi.DLanguageFile;
import net.masterthought.dlanguage.psi.DLanguageIdentifierChain;
import net.masterthought.dlanguage.psi.DLanguageModuleDeclaration;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import net.masterthought.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import net.masterthought.dlanguage.psi.references.DReference;
import net.masterthought.dlanguage.stubs.DLanguageModuleDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import static net.masterthought.dlanguage.psi.DLanguageTypes.KW_MODULE;
import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_SCOLON;

public class DLanguageModuleDeclarationImpl extends DNamedStubbedPsiElementBase<DLanguageModuleDeclarationStub> implements DLanguageModuleDeclaration {

    public DLanguageModuleDeclarationImpl(final DLanguageModuleDeclarationStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DLanguageModuleDeclarationImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DLanguageVisitor visitor) {
        visitor.visitModuleDeclaration(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public String getName() {
        if (getStub() != null) {
            return getStub().getName();
        }
        if (getIdentifierChain() == null) {
            return DReference.Companion.getNAME_NOT_FOUND_STRING();
        }
        return getIdentifierChain().getText();
    }

    @Nullable
    public PsiElement getNameIdentifier() {
        final ASTNode keyNode = getNode();
        return keyNode.getPsi();
    }

    @NotNull
    public PsiReference getReference() {
        return new DReference(this, TextRange.from(0, (this).getName().length()));
    }

    @NotNull
    public PsiElement setName(@NotNull final String newName) {
        //todo
        return null;
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
            public Icon getIcon(final boolean unused) {
                return DLanguageIcons.FILE;
            }
        };
    }

    @Nullable
    @Override
    public PsiElement getKW_MODULE() {
        return findChildByType(KW_MODULE);
    }

    @Nullable
    @Override
    public DLanguageIdentifierChain getIdentifierChain() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierChain.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }
}
