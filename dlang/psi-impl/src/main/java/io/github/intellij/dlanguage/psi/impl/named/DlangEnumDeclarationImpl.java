package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.named.DlangEnumDeclaration;
import io.github.intellij.dlanguage.stubs.DlangEnumDeclarationStub;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DlangEnumDeclarationImpl extends
    DNamedStubbedPsiElementBase<DlangEnumDeclarationStub> implements
    DlangEnumDeclaration {

    public DlangEnumDeclarationImpl(final DlangEnumDeclarationStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DlangEnumDeclarationImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitDNamedElement(this);visitor.visitEnumDeclaration(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public DLanguageEnumBody getEnumBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageEnumBody.class);
    }

    @Override
    @Nullable
    public PsiElement getIdentifier() {
        return findChildByType(DlangTypes.ID);
    }

    @Nullable
    @Override
    public PsiElement getOP_COLON() {
        return findChildByType(DlangTypes.OP_COLON);
    }

    @Nullable
    @Override
    public PsiElement getKW_ENUM() {
        return findChildByType(DlangTypes.KW_ENUM);
    }

    @Nullable
    @Override
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    public PsiElement getNameIdentifier() {
        return getIdentifier();
    }
}
