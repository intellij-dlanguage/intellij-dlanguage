package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 4/14/2017.
 *
 * todo: check if this is still needed. Seems to have been replaced by DStubbedPsiElementBase
 */
public abstract class DStubBasedPsiElementBase<T extends StubElement<? extends PsiElement>> extends StubBasedPsiElementBase<T> implements StubBasedPsiElement<T> {
    public DStubBasedPsiElementBase(@NotNull final T stub, @NotNull final IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DStubBasedPsiElementBase(@NotNull final ASTNode node) {
        super(node);
    }

    public DStubBasedPsiElementBase(final T stub, final IElementType nodeType, final ASTNode node) {
        super(stub, nodeType, node);
    }

    @Override
    public String toString() {
//        return this.getElementTypeImpl().toString();
        return this.getIElementType().toString();
    }
}
