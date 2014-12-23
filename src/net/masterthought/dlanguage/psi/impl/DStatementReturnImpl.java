package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementReturn;

public class DStatementReturnImpl extends DCompositeElementImpl implements DStatementReturn{

    public DStatementReturnImpl(ASTNode node) {
        super(node);
    }


}
