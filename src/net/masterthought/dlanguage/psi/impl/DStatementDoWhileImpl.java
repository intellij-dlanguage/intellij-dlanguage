package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementDoWhile;

public class DStatementDoWhileImpl extends DCompositeElementImpl implements DStatementDoWhile{

    public DStatementDoWhileImpl(ASTNode node) {
        super(node);
    }


}
