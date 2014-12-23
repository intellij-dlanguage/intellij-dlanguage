package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementThrow;

public class DStatementThrowImpl extends DCompositeElementImpl implements DStatementThrow{

    public DStatementThrowImpl(ASTNode node) {
        super(node);
    }


}
