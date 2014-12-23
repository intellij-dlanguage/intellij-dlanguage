package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpLiteralArray;

public class DExpLiteralArrayImpl extends DCompositeElementImpl implements DExpLiteralArray{

    public DExpLiteralArrayImpl(ASTNode node) {
        super(node);
    }


}
