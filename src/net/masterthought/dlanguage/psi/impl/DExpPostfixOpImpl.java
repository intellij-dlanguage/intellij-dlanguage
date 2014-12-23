package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpPostfixOp;

public class DExpPostfixOpImpl extends DCompositeElementImpl implements DExpPostfixOp{

    public DExpPostfixOpImpl(ASTNode node) {
        super(node);
    }


}
