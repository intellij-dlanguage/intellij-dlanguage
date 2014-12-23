package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementWith;

public class DStatementWithImpl extends DCompositeElementImpl implements DStatementWith{

    public DStatementWithImpl(ASTNode node) {
        super(node);
    }


}
