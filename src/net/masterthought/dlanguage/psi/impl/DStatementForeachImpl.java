package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementForeach;

public class DStatementForeachImpl extends DCompositeElementImpl implements DStatementForeach{

    public DStatementForeachImpl(ASTNode node) {
        super(node);
    }


}
