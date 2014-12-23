package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DArrayInitEntry;

public class DArrayInitEntryImpl extends DCompositeElementImpl implements DArrayInitEntry{

    public DArrayInitEntryImpl(ASTNode node) {
        super(node);
    }


}
