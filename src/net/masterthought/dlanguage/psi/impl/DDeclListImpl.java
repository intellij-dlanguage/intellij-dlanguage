package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDeclList;

public class DDeclListImpl extends DCompositeElementImpl implements DDeclList{

    public DDeclListImpl(ASTNode node) {
        super(node);
    }


}
