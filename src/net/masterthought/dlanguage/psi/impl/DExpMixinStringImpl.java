package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpMixinString;

public class DExpMixinStringImpl extends DCompositeElementImpl implements DExpMixinString{

    public DExpMixinStringImpl(ASTNode node) {
        super(node);
    }


}
