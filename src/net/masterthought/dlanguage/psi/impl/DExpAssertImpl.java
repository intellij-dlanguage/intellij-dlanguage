package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpAssert;

public class DExpAssertImpl extends DCompositeElementImpl implements DExpAssert{

    public DExpAssertImpl(ASTNode node) {
        super(node);
    }


}
