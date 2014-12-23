package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DEnumBody;

public class DEnumBodyImpl extends DCompositeElementImpl implements DEnumBody{

    public DEnumBodyImpl(ASTNode node) {
        super(node);
    }


}
