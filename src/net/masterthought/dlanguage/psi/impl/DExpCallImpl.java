package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpCall;

public class DExpCallImpl extends DCompositeElementImpl implements DExpCall{

    public DExpCallImpl(ASTNode node) {
        super(node);
    }


}
