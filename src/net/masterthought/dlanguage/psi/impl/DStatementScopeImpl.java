package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementScope;

public class DStatementScopeImpl extends DCompositeElementImpl implements DStatementScope{

    public DStatementScopeImpl(ASTNode node) {
        super(node);
    }


}
