package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DSimpleVariableDef;

public class DSimpleVariableDefImpl extends DCompositeElementImpl implements DSimpleVariableDef{

    public DSimpleVariableDefImpl(ASTNode node) {
        super(node);
    }


}
