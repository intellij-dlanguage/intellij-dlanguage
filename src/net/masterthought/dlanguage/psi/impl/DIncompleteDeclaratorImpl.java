package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DIncompleteDeclarator;

public class DIncompleteDeclaratorImpl extends DCompositeElementImpl implements DIncompleteDeclarator{

    public DIncompleteDeclaratorImpl(ASTNode node) {
        super(node);
    }


}
