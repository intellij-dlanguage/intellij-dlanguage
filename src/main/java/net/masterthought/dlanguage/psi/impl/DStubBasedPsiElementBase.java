package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 4/14/2017.
 */
public class DStubBasedPsiElementBase<T extends StubElement<? extends PsiElement>> extends StubBasedPsiElementBase<T>{
    public DStubBasedPsiElementBase(@NotNull T stub, @NotNull IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DStubBasedPsiElementBase(@NotNull ASTNode node) {
        super(node);
    }

    public DStubBasedPsiElementBase(T stub, IElementType nodeType, ASTNode node) {
        super(stub, nodeType, node);
    }

    @Override
    public String toString() {
        return getElementType().toString();
    }
}
