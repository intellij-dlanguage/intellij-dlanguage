package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDeclarationInvariant;

public class DDeclarationInvariantImpl extends DCompositeElementImpl implements DDeclarationInvariant{

    public DDeclarationInvariantImpl(ASTNode node) {
        super(node);
    }


}
