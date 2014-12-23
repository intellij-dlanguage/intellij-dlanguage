package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpNewAnonClass;

public class DExpNewAnonClassImpl extends DCompositeElementImpl implements DExpNewAnonClass{

    public DExpNewAnonClassImpl(ASTNode node) {
        super(node);
    }


}
