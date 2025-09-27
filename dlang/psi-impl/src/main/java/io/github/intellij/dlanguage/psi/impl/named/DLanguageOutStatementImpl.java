package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import io.github.intellij.dlanguage.psi.DLanguageBlockStatement;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.impl.DElementFactory;
import io.github.intellij.dlanguage.psi.named.DLanguageOutStatement;
import io.github.intellij.dlanguage.psi.types.DType;
import io.github.intellij.dlanguage.psi.types.DUnknownType;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributesFinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageOutStatementImpl extends ASTWrapperPsiElement implements
    DLanguageOutStatement {

    public DLanguageOutStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitOutStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getIdentifier() {
        return findChildByType(ID);
    }

    @Nullable
    public DLanguageBlockStatement getBlockStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageBlockStatement.class);
    }

    @Nullable
    public PsiElement getKW_OUT() {
        return findChildByType(KW_OUT);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(OP_PAR_LEFT);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(OP_PAR_RIGHT);
    }

    @Override
    public DAttributes getAttributes() {
        return null;
    }

    @Override
    public boolean isPublic() {
        return false;
    }

    @Override
    public boolean isProtected() {
        return false;
    }

    @Override
    public boolean isPrivate() {
        return true;
    }

    @Override
    public DAttributesFinder.Visibility visibility() {
        return DAttributesFinder.Visibility.PRIVATE;
    }

    @Override
    public boolean isProperty() {
        return false;
    }

    @Override
    public boolean isNoGC() {
        return false;
    }

    @Override
    public boolean isExtern() {
        return false;
    }

    @Override
    public boolean isPure() {
        return false;
    }

    @Override
    public boolean isNothrow() {
        return false;
    }

    @Override
    public boolean isConst() {
        return false;
    }

    @Override
    public boolean isImmutable() {
        return false;
    }

    @Override
    public boolean isEnum() {
        return false;
    }

    @Override
    public @Nullable PsiElement getNameIdentifier() {
        return getIdentifier();
    }

    @Override
    public PsiElement setName(@NlsSafe @NotNull String name) throws IncorrectOperationException {
        if (getNameIdentifier() == null) {
            throw new IncorrectOperationException("Cannot rename. Identifier was Null");
        }
        getNameIdentifier().replace(
            Objects.requireNonNull(DElementFactory.createDLanguageIdentifierFromText(
                getProject(),
                name
            ))
        );
        return this;
    }

    @Override
    public @NotNull DType getDType() {
        // TODO the type is the return type of it’s parent class
        return new DUnknownType();
    }
}
