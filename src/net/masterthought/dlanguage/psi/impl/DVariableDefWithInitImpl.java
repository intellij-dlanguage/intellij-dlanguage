package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DVariableDefWithInit;

public class DVariableDefWithInitImpl extends DCompositeElementImpl implements DVariableDefWithInit{

    public DVariableDefWithInitImpl(ASTNode node) {
        super(node);
    }


}
