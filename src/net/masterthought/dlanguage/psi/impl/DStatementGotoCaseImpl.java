package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementGotoCase;

public class DStatementGotoCaseImpl extends DCompositeElementImpl implements DStatementGotoCase{

    public DStatementGotoCaseImpl(ASTNode node) {
        super(node);
    }


}
