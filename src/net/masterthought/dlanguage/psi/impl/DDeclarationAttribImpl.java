package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDeclarationAttrib;

public class DDeclarationAttribImpl extends DCompositeElementImpl implements DDeclarationAttrib{

    public DDeclarationAttribImpl(ASTNode node) {
        super(node);
    }


}
