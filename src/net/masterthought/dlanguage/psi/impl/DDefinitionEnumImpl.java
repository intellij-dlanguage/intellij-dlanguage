package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionEnum;

public class DDefinitionEnumImpl extends DCompositeElementImpl implements DDefinitionEnum{

    public DDefinitionEnumImpl(ASTNode node) {
        super(node);
    }


}
