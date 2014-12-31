package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;

public abstract class DStubbedPsiElementBase<T extends StubElement<?>> extends StubBasedPsiElementBase<T> implements DCompositeElement {
    public DStubbedPsiElementBase(T stub, IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DStubbedPsiElementBase(ASTNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return getElementType().toString();
    }
}

