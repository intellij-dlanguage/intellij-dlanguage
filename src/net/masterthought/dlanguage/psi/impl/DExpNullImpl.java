package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpNull;

public class DExpNullImpl extends DCompositeElementImpl implements DExpNull{

    public DExpNullImpl(ASTNode node) {
        super(node);
    }


}
