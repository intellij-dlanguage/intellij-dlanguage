package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDeclBlock;

public class DDeclBlockImpl extends DCompositeElementImpl implements DDeclBlock{

    public DDeclBlockImpl(ASTNode node) {
        super(node);
    }


}
