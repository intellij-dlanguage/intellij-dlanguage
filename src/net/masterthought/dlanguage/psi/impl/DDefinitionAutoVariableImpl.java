package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionAutoVariable;

public class DDefinitionAutoVariableImpl extends DCompositeElementImpl implements DDefinitionAutoVariable{

    public DDefinitionAutoVariableImpl(ASTNode node) {
        super(node);
    }


}
