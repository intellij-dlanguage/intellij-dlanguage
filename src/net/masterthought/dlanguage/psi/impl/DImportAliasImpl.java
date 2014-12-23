package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DImportAlias;

public class DImportAliasImpl extends DCompositeElementImpl implements DImportAlias{

    public DImportAliasImpl(ASTNode node) {
        super(node);
    }


}
