package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DInvalidSyntax;

public class DInvalidSyntaxImpl extends DCompositeElementImpl implements DInvalidSyntax{

    public DInvalidSyntaxImpl(ASTNode node) {
        super(node);
    }


}
