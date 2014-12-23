package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DRefTypeof;

public class DRefTypeofImpl extends DCompositeElementImpl implements DRefTypeof{

    public DRefTypeofImpl(ASTNode node) {
        super(node);
    }


}
