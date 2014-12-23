package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DRefTemplateInstance;

public class DRefTemplateInstanceImpl extends DCompositeElementImpl implements DRefTemplateInstance{

    public DRefTemplateInstanceImpl(ASTNode node) {
        super(node);
    }


}
