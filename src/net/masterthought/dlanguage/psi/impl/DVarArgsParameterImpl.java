package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DVarArgsParameter;

public class DVarArgsParameterImpl extends DCompositeElementImpl implements DVarArgsParameter{

    public DVarArgsParameterImpl(ASTNode node) {
        super(node);
    }


}
