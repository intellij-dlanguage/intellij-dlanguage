package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionStruct;

public class DDefinitionStructImpl extends DCompositeElementImpl implements DDefinitionStruct{

    public DDefinitionStructImpl(ASTNode node) {
        super(node);
    }


}
