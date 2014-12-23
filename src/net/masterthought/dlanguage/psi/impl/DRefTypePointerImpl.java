package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DRefTypePointer;

public class DRefTypePointerImpl extends DCompositeElementImpl implements DRefTypePointer{

    public DRefTypePointerImpl(ASTNode node) {
        super(node);
    }


}
