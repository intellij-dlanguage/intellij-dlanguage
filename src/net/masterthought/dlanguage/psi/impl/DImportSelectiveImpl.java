package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DImportSelective;

public class DImportSelectiveImpl extends DCompositeElementImpl implements DImportSelective{

    public DImportSelectiveImpl(ASTNode node) {
        super(node);
    }


}
