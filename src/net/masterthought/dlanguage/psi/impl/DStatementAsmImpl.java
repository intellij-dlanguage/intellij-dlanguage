package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementAsm;

public class DStatementAsmImpl extends DCompositeElementImpl implements DStatementAsm{

    public DStatementAsmImpl(ASTNode node) {
        super(node);
    }


}
