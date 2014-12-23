package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DAliasVarDeclFragment;

public class DAliasVarDeclFragmentImpl extends DCompositeElementImpl implements DAliasVarDeclFragment{

    public DAliasVarDeclFragmentImpl(ASTNode node) {
        super(node);
    }


}
