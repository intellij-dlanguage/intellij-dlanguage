package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionClass;

public class DDefinitionClassImpl extends DCompositeElementImpl implements DDefinitionClass{

    public DDefinitionClassImpl(ASTNode node) {
        super(node);
    }


}
