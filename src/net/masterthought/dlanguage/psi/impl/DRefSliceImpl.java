package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DRefSlice;

public class DRefSliceImpl extends DCompositeElementImpl implements DRefSlice{

    public DRefSliceImpl(ASTNode node) {
        super(node);
    }


}
