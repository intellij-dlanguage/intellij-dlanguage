package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementCase;

public class DStatementCaseImpl extends DCompositeElementImpl implements DStatementCase{

    public DStatementCaseImpl(ASTNode node) {
        super(node);
    }


}
