package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementIfVar;

public class DStatementIfVarImpl extends DCompositeElementImpl implements DStatementIfVar{

    public DStatementIfVarImpl(ASTNode node) {
        super(node);
    }


}
