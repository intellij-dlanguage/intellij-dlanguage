package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DRefQualified;

public class DRefQualifiedImpl extends DCompositeElementImpl implements DRefQualified{

    public DRefQualifiedImpl(ASTNode node) {
        super(node);
    }


}
