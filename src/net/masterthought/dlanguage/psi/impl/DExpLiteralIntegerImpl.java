package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpLiteralInteger;

public class DExpLiteralIntegerImpl extends DCompositeElementImpl implements DExpLiteralInteger{

    public DExpLiteralIntegerImpl(ASTNode node) {
        super(node);
    }


}
