package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import io.github.intellij.dlanguage.psi.DLanguageAliasDeclaration;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.psi.named.DLanguageDeclaratorIdentifier;
import io.github.intellij.dlanguage.psi.types.DType;
import io.github.intellij.dlanguage.psi.types.DUnknownType;
import io.github.intellij.dlanguage.stubs.DLanguageDeclaratorIdentifierStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class DlangDeclaratorIdentifierImpl extends
    DNamedStubbedPsiElementBase<DLanguageDeclaratorIdentifierStub> implements
    DLanguageDeclaratorIdentifier {
    public DlangDeclaratorIdentifierImpl(@NotNull DLanguageDeclaratorIdentifierStub stub, IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DlangDeclaratorIdentifierImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitDNamedElement(this);
        visitor.visitDeclaratorIdentifier(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    public @Nullable PsiElement getNameIdentifier() {
        return getIdentifier();
    }

    @Override
    public @Nullable PsiElement getIdentifier() {
        return findChildByType(DlangTypes.ID);
    }

    @Override
    public @Nullable PsiElement getOP_COMMA() {
        return findChildByType(DlangTypes.OP_COMMA);
    }

    @Override
    @NotNull
    public DType getDType() {
        if (getParent() instanceof DLanguageAliasDeclaration dLanguageAliasDeclaration) {
            return Objects.requireNonNull(dLanguageAliasDeclaration.getType()).getDType();
        }
        assert false : "Unreachable statement, alias in old form must be child of AliasDeclaration";
        return new DUnknownType();
    }
}
