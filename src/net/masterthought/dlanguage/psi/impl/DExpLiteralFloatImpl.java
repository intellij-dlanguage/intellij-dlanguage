package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpLiteralFloat;

public class DExpLiteralFloatImpl extends DCompositeElementImpl implements DExpLiteralFloat{

    public DExpLiteralFloatImpl(ASTNode node) {
        super(node);
    }


}
