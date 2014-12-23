package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementSwitch;

public class DStatementSwitchImpl extends DCompositeElementImpl implements DStatementSwitch{

    public DStatementSwitchImpl(ASTNode node) {
        super(node);
    }


}
