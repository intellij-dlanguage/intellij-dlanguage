package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDeclarationMixin;

public class DDeclarationMixinImpl extends DCompositeElementImpl implements DDeclarationMixin{

    public DDeclarationMixinImpl(ASTNode node) {
        super(node);
    }


}
