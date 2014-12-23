package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DFunctionBody;

public class DFunctionBodyImpl extends DCompositeElementImpl implements DFunctionBody{

    public DFunctionBodyImpl(ASTNode node) {
        super(node);
    }


}
