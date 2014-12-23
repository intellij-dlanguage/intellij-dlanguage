package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionInterface;

public class DDefinitionInterfaceImpl extends DCompositeElementImpl implements DDefinitionInterface{

    public DDefinitionInterfaceImpl(ASTNode node) {
        super(node);
    }


}
