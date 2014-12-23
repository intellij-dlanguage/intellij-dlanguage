package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpArrayLength;

public class DExpArrayLengthImpl extends DCompositeElementImpl implements DExpArrayLength{

    public DExpArrayLengthImpl(ASTNode node) {
        super(node);
    }


}
