package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DRefModule;

public class DRefModuleImpl extends DCompositeElementImpl implements DRefModule{

    public DRefModuleImpl(ASTNode node) {
        super(node);
    }


}
