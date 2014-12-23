package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DRefIndexing;

public class DRefIndexingImpl extends DCompositeElementImpl implements DRefIndexing{

    public DRefIndexingImpl(ASTNode node) {
        super(node);
    }


}
