package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpIndex;

public class DExpIndexImpl extends DCompositeElementImpl implements DExpIndex{

    public DExpIndexImpl(ASTNode node) {
        super(node);
    }


}
