package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementTry;

public class DStatementTryImpl extends DCompositeElementImpl implements DStatementTry{

    public DStatementTryImpl(ASTNode node) {
        super(node);
    }


}
