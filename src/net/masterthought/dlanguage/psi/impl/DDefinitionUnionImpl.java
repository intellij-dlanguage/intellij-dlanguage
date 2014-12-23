package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionUnion;

public class DDefinitionUnionImpl extends DCompositeElementImpl implements DDefinitionUnion{

    public DDefinitionUnionImpl(ASTNode node) {
        super(node);
    }


}
