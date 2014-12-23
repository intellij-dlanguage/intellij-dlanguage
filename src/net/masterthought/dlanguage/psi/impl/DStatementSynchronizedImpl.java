package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementSynchronized;

public class DStatementSynchronizedImpl extends DCompositeElementImpl implements DStatementSynchronized{

    public DStatementSynchronizedImpl(ASTNode node) {
        super(node);
    }


}
