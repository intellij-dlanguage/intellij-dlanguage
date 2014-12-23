package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DModule;

public class DModuleImpl extends DCompositeElementImpl implements DModule{

    public DModuleImpl(ASTNode node) {
        super(node);
    }


}
