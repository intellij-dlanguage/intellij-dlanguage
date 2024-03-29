package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import io.github.intellij.dlanguage.psi.interfaces.DCompositeElement;
import io.github.intellij.dlanguage.psi.interfaces.DCompositeElement;

public class DCompositeElementImpl extends ASTWrapperPsiElement implements DCompositeElement {
    public DCompositeElementImpl(final ASTNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return getNode().getElementType().toString();
    }

}
