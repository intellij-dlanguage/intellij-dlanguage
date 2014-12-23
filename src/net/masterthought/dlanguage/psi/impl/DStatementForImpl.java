package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementFor;

public class DStatementForImpl extends DCompositeElementImpl implements DStatementFor{

    public DStatementForImpl(ASTNode node) {
        super(node);
    }


}
