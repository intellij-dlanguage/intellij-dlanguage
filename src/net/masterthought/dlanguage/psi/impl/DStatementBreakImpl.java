package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementBreak;

public class DStatementBreakImpl extends DCompositeElementImpl implements DStatementBreak{

    public DStatementBreakImpl(ASTNode node) {
        super(node);
    }


}
