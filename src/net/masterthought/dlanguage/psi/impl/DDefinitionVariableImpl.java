package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionVariable;

public class DDefinitionVariableImpl extends DCompositeElementImpl implements DDefinitionVariable{

    public DDefinitionVariableImpl(ASTNode node) {
        super(node);
    }


}
