package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementDefault;

public class DStatementDefaultImpl extends DCompositeElementImpl implements DStatementDefault{

    public DStatementDefaultImpl(ASTNode node) {
        super(node);
    }


}
