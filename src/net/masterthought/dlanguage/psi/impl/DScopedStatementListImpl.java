package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DScopedStatementList;

public class DScopedStatementListImpl extends DCompositeElementImpl implements DScopedStatementList{

    public DScopedStatementListImpl(ASTNode node) {
        super(node);
    }


}
