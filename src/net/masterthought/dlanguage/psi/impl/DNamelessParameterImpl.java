package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DNamelessParameter;

public class DNamelessParameterImpl extends DCompositeElementImpl implements DNamelessParameter{

    public DNamelessParameterImpl(ASTNode node) {
        super(node);
    }


}
