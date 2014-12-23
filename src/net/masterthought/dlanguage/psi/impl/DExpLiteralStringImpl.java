package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpLiteralString;

public class DExpLiteralStringImpl extends DCompositeElementImpl implements DExpLiteralString{

    public DExpLiteralStringImpl(ASTNode node) {
        super(node);
    }


}
