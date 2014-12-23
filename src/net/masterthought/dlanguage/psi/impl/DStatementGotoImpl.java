package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementGoto;

public class DStatementGotoImpl extends DCompositeElementImpl implements DStatementGoto{

    public DStatementGotoImpl(ASTNode node) {
        super(node);
    }


}
