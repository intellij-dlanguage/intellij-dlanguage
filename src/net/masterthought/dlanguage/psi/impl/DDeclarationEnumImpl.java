package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDeclarationEnum;

public class DDeclarationEnumImpl extends DCompositeElementImpl implements DDeclarationEnum{

    public DDeclarationEnumImpl(ASTNode node) {
        super(node);
    }


}
