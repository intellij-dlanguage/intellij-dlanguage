package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.named.DLanguageModuleDeclaration;
import io.github.intellij.dlanguage.psi.impl.DElementFactory;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.stubs.DLanguageModuleDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DlangModuleDeclarationImpl extends DNamedStubbedPsiElementBase<DLanguageModuleDeclarationStub> implements DLanguageModuleDeclaration {

    public DlangModuleDeclarationImpl(final DLanguageModuleDeclarationStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DlangModuleDeclarationImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitDNamedElement(this);
        visitor.visitModuleDeclaration(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public String getName() {
        final DLanguageModuleDeclarationStub greenStub = getGreenStub();
        if (greenStub != null) {
            return greenStub.getName();
        }
        if (getIdentifierChain() == null) {
            return getContainingFile().getName().replace(".d", "");
        }
        return getIdentifierChain().getText();
    }

    @Nullable
    public PsiElement getNameIdentifier() {
        return getIdentifierChain();
    }

    @NotNull
    public PsiElement setName(@NotNull final String newName) {
        return this.replace(DElementFactory.createDLanguageModuleFromText(this.getProject(), newName));
    }

    @NotNull
    public ItemPresentation getPresentation() {
        return new DlangItemPresentation(getContainingFile()) {
            @NotNull
            @Override
            public String getPresentableText() {
                return getName();
            }
        };
    }

    public boolean hasAName() {
        return getIdentifierChain() != null;
    }

    @Nullable
    @Override
    public List<DLanguageAtAttribute> getAtAttributes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAtAttribute.class);
    }
    @Nullable
    @Override
    public DLanguageDeprecated getDeprecated() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDeprecated.class);
    }

    @Nullable
    @Override
    public PsiElement getKW_MODULE() {
        return findChildByType(DlangTypes.KW_MODULE);
    }

    @Nullable
    @Override
    public DLanguageIdentifierChain getIdentifierChain() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierChain.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_SCOLON() {
        return findChildByType(DlangTypes.OP_SCOLON);
    }
}
