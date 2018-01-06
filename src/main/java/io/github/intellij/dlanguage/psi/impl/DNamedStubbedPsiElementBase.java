package io.github.intellij.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.stubs.IStubElementType;
import io.github.intellij.dlanguage.icons.DlangIcons;
import io.github.intellij.dlanguage.psi.DlangFile;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.references.DReference;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributesFinder;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributesFinder.Visibility;
import io.github.intellij.dlanguage.stubs.DNamedStubBase;
import javax.swing.Icon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class DNamedStubbedPsiElementBase<T extends DNamedStubBase<?>> extends
    DStubbedPsiElementBase<T> implements DNamedElement {

    public DNamedStubbedPsiElementBase(@NotNull final T stub, final IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DNamedStubbedPsiElementBase(final ASTNode node) {
        super(node);
    }

    @Nullable
    public abstract DlangIdentifier getNameIdentifier();

    @NotNull
    public String getName() {
        final T stub = getGreenStub();
        final String name = stub != null ? stub.getName() : null;
        if (name != null) {
            return name;
        }

        final DlangIdentifier identifier = getNameIdentifier();
        return identifier != null ? identifier.getName()
            : DReference.Companion.getNAME_NOT_FOUND_STRING();
    }

    @NotNull
    public PsiElement setName(@NotNull final String newName) {
        if (getNameIdentifier() == null) {
            throw new IllegalStateException("Cannot rename. Identifier was Null");
        }
        return getNameIdentifier().setName(newName);
    }

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
                return psiFile instanceof DlangFile ? ((DlangFile) psiFile).getModuleOrFileName()
                    : null;
            }

            @Nullable
            @Override
            public Icon getIcon(final boolean unused) {
                return DlangIcons.FILE;
            }
        };
    }

    public DAttributes getAttributes() {
        if (getGreenStub() != null) {
            return getGreenStub().getAttributes();
        }
        final DAttributesFinder finder = new DAttributesFinder(this);
        finder.recurseUp();
        return finder.getAttributes();
    }

    @NotNull
    public PsiReference getReference() {
        return new DReference(this, TextRange.from(0, (this).getName().length()));
    }//not sure if this should only be implemented for identifier todo

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

