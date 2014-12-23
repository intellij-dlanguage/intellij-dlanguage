package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDeclarationStaticIf;

public class DDeclarationStaticIfImpl extends DCompositeElementImpl implements DDeclarationStaticIf{

    public DDeclarationStaticIfImpl(ASTNode node) {
        super(node);
    }


}
