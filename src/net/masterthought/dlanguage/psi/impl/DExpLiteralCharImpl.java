package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpLiteralChar;

public class DExpLiteralCharImpl extends DCompositeElementImpl implements DExpLiteralChar{

    public DExpLiteralCharImpl(ASTNode node) {
        super(node);
    }


}
