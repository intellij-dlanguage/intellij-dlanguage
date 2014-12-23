package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementWhile;

public class DStatementWhileImpl extends DCompositeElementImpl implements DStatementWhile{

    public DStatementWhileImpl(ASTNode node) {
        super(node);
    }


}
