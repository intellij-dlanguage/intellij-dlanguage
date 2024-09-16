package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;

public abstract class DStubbedPsiElementBase<T extends StubElement<? extends PsiElement>> extends StubBasedPsiElementBase<T>
    implements StubBasedPsiElement<T> {
    public DStubbedPsiElementBase(final T stub, final IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DStubbedPsiElementBase(final ASTNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return getElementType().toString();
    }

    //todo getNameIdentifier needs to use stubs
}

