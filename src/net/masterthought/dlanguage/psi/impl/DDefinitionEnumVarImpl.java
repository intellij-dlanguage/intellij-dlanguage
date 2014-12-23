package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionEnumVar;

public class DDefinitionEnumVarImpl extends DCompositeElementImpl implements DDefinitionEnumVar{

    public DDefinitionEnumVarImpl(ASTNode node) {
        super(node);
    }


}
