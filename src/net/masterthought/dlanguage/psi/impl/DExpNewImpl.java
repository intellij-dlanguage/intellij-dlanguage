package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpNew;

public class DExpNewImpl extends DCompositeElementImpl implements DExpNew{

    public DExpNewImpl(ASTNode node) {
        super(node);
    }


}
