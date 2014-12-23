package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DMissingDeclaration;

public class DMissingDeclarationImpl extends DCompositeElementImpl implements DMissingDeclaration{

    public DMissingDeclarationImpl(ASTNode node) {
        super(node);
    }


}
