package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionVarFragment;

public class DDefinitionVarFragmentImpl extends DCompositeElementImpl implements DDefinitionVarFragment{

    public DDefinitionVarFragmentImpl(ASTNode node) {
        super(node);
    }


}
