package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpConditional;

public class DExpConditionalImpl extends DCompositeElementImpl implements DExpConditional{

    public DExpConditionalImpl(ASTNode node) {
        super(node);
    }


}
