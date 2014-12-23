package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpCast;

public class DExpCastImpl extends DCompositeElementImpl implements DExpCast{

    public DExpCastImpl(ASTNode node) {
        super(node);
    }


}
