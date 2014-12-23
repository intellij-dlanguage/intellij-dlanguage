package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementContinue;

public class DStatementContinueImpl extends DCompositeElementImpl implements DStatementContinue{

    public DStatementContinueImpl(ASTNode node) {
        super(node);
    }


}
