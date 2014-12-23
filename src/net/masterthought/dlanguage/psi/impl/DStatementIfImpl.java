package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementIf;

public class DStatementIfImpl extends DCompositeElementImpl implements DStatementIf{

    public DStatementIfImpl(ASTNode node) {
        super(node);
    }


}
