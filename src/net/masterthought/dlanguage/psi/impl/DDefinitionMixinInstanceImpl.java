package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionMixinInstance;

public class DDefinitionMixinInstanceImpl extends DCompositeElementImpl implements DDefinitionMixinInstance{

    public DDefinitionMixinInstanceImpl(ASTNode node) {
        super(node);
    }


}
