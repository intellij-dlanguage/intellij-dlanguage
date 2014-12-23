package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpLiteralBool;

public class DExpLiteralBoolImpl extends DCompositeElementImpl implements DExpLiteralBool{

    public DExpLiteralBoolImpl(ASTNode node) {
        super(node);
    }


}
