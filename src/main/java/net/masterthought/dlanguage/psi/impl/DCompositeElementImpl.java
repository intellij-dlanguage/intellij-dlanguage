package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.DCompositeElement;

public class DCompositeElementImpl extends ASTWrapperPsiElement implements DCompositeElement {
    public DCompositeElementImpl(ASTNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return getNode().getElementType().toString();
    }

}
