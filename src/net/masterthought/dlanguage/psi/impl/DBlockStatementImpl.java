package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DBlockStatement;

public class DBlockStatementImpl extends DCompositeElementImpl implements DBlockStatement{

    public DBlockStatementImpl(ASTNode node) {
        super(node);
    }


}
