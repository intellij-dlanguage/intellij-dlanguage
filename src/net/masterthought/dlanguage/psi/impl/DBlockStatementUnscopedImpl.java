package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DBlockStatementUnscoped;

public class DBlockStatementUnscopedImpl extends DCompositeElementImpl implements DBlockStatementUnscoped{

    public DBlockStatementUnscopedImpl(ASTNode node) {
        super(node);
    }


}
