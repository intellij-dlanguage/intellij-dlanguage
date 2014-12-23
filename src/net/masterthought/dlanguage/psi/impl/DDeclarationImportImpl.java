package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDeclarationImport;

public class DDeclarationImportImpl extends DCompositeElementImpl implements DDeclarationImport{

    public DDeclarationImportImpl(ASTNode node) {
        super(node);
    }


}
