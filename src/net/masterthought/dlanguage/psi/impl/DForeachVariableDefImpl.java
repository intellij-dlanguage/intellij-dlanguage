package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DForeachVariableDef;

public class DForeachVariableDefImpl extends DCompositeElementImpl implements DForeachVariableDef{

    public DForeachVariableDefImpl(ASTNode node) {
        super(node);
    }


}
