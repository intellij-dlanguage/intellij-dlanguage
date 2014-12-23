package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpReference;

public class DExpReferenceImpl extends DCompositeElementImpl implements DExpReference{

    public DExpReferenceImpl(ASTNode node) {
        super(node);
    }


}
