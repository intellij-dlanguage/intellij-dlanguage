package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DRefImportSelection;

public class DRefImportSelectionImpl extends DCompositeElementImpl implements DRefImportSelection{

    public DRefImportSelectionImpl(ASTNode node) {
        super(node);
    }


}
