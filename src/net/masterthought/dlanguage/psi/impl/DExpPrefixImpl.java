package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpPrefix;

public class DExpPrefixImpl extends DCompositeElementImpl implements DExpPrefix{

    public DExpPrefixImpl(ASTNode node) {
        super(node);
    }


}
