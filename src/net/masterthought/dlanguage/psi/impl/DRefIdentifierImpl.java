package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DRefIdentifier;

public class DRefIdentifierImpl extends DCompositeElementImpl implements DRefIdentifier{

    public DRefIdentifierImpl(ASTNode node) {
        super(node);
    }


}
