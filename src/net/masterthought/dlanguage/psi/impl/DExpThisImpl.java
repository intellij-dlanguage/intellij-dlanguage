package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpThis;

public class DExpThisImpl extends DCompositeElementImpl implements DExpThis{

    public DExpThisImpl(ASTNode node) {
        super(node);
    }


}
