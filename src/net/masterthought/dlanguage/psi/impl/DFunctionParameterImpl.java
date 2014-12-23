package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DFunctionParameter;

public class DFunctionParameterImpl extends DCompositeElementImpl implements DFunctionParameter{

    public DFunctionParameterImpl(ASTNode node) {
        super(node);
    }


}
