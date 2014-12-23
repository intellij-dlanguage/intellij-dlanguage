package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DInitializerArray;

public class DInitializerArrayImpl extends DCompositeElementImpl implements DInitializerArray{

    public DInitializerArrayImpl(ASTNode node) {
        super(node);
    }


}
