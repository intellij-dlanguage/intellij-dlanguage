package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementExpression;

public class DStatementExpressionImpl extends DCompositeElementImpl implements DStatementExpression{

    public DStatementExpressionImpl(ASTNode node) {
        super(node);
    }


}
