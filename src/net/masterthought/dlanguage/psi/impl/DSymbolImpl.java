package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DSymbol;

public class DSymbolImpl extends DCompositeElementImpl implements DSymbol{

    public DSymbolImpl(ASTNode node) {
        super(node);
    }


}
