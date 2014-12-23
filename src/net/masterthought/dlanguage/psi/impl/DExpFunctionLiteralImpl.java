package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpFunctionLiteral;

public class DExpFunctionLiteralImpl extends DCompositeElementImpl implements DExpFunctionLiteral{

    public DExpFunctionLiteralImpl(ASTNode node) {
        super(node);
    }


}
