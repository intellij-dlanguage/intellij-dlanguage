package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpInfix;

public class DExpInfixImpl extends DCompositeElementImpl implements DExpInfix{

    public DExpInfixImpl(ASTNode node) {
        super(node);
    }


}
