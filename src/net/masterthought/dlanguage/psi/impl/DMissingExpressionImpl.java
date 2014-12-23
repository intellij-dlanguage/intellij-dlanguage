package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DMissingExpression;

public class DMissingExpressionImpl extends DCompositeElementImpl implements DMissingExpression{

    public DMissingExpressionImpl(ASTNode node) {
        super(node);
    }


}
