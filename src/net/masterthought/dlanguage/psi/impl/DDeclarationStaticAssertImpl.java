package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDeclarationStaticAssert;

public class DDeclarationStaticAssertImpl extends DCompositeElementImpl implements DDeclarationStaticAssert{

    public DDeclarationStaticAssertImpl(ASTNode node) {
        super(node);
    }


}
