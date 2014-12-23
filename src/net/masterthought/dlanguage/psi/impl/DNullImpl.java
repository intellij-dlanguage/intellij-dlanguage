package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DNull;

public class DNullImpl extends DCompositeElementImpl implements DNull{

    public DNullImpl(ASTNode node) {
        super(node);
    }


}
