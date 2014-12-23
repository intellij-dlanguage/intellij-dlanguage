package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DEmptyStatement;

public class DEmptyStatementImpl extends DCompositeElementImpl implements DEmptyStatement{

    public DEmptyStatementImpl(ASTNode node) {
        super(node);
    }


}
