package io.github.intellij.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.PsiImplUtil;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.util.IncorrectOperationException;
import io.github.intellij.dlanguage.psi.DlangItemPresentation;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.references.DReference;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributesFinder;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributesFinder.Visibility;
import io.github.intellij.dlanguage.stubs.DNamedStubBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class DNamedStubbedPsiElementBase<T extends DNamedStubBase<?>> extends
    DStubbedPsiElementBase<T> implements DNamedElement {

    public DNamedStubbedPsiElementBase(@NotNull final T stub, final IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DNamedStubbedPsiElementBase(final ASTNode node) {
        super(node);
    }

    @Nullable
    public abstract PsiElement getNameIdentifier();

    @NotNull
    public String getName() {
        final T stub = getGreenStub();
        final String name = stub != null ? stub.getName() : null;
        if (name != null) {
            return name;
        }

        final PsiElement identifier = getNameIdentifier();
        return identifier != null ? identifier.getText()
            : DReference.Companion.getNAME_NOT_FOUND_STRING();
    }

    @NotNull
    public PsiElement setName(@NotNull final String newName) {
        if (getNameIdentifier() == null) {
            throw new IncorrectOperationException("Cannot rename. Identifier was Null");
        }
        PsiImplUtil.setName(Objects.requireNonNull(getNameIdentifier()), newName);
        return this;
    }

    public ItemPresentation getPresentation() {
        return new DlangItemPresentation(getContainingFile()) {
            @NotNull
            @Override
            public String getPresentableText() {
                return getName();
            }
        };
    }

    public DAttributes getAttributes() {
        //todo this can be slow and have an O(n^2) complexity during indexing
        if (getGreenStub() != null) {
            return getGreenStub().getAttributes();
        }
        final DAttributesFinder finder = new DAttributesFinder(this);
        finder.recurseUp();
        return finder.getAttributes();
    }

    @Override
    public Visibility visibility() {
        return getAttributes().getVisibility();
    }

    public boolean isPublic() {
        return getAttributes().getVisibility() == Visibility.PUBLIC;
    }

    public boolean isProtected() {
        return getAttributes().getVisibility() == Visibility.PROTECTED;
    }

    public boolean isPrivate() {
        return getAttributes().getVisibility() == Visibility.PRIVATE;
    }

    public boolean isProperty() {
        return getAttributes().getProperty();
    }

    public boolean isNoGC() {
        return getAttributes().getNoGC();
    }

    public boolean isExtern() {
        return getAttributes().getExtern();
    }

    public boolean isPure() {
        return getAttributes().getPure();
    }

    public boolean isNothrow() {
        return getAttributes().getNothrow();
    }

    public boolean isConst() {
        return getAttributes().getConst();
    }

    public boolean isImmutable() {
        return getAttributes().getImmutable();
    }

    public boolean isEnum() {
        return getAttributes().getEnum();
    }

}

