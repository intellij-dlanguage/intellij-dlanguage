package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.NotNull;

public abstract class DNamedStubbedPsiElementBase<T extends StubElement<?>> extends DStubbedPsiElementBase<T> implements DNamedElement {
    public DNamedStubbedPsiElementBase(@NotNull T stub, IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DNamedStubbedPsiElementBase(ASTNode node) {
        super(node);
    }

    @Override
    @NotNull
    public String getName() {
        return super.getName();
    }
}

