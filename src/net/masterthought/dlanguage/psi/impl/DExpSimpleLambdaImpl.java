package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpSimpleLambda;

public class DExpSimpleLambdaImpl extends DCompositeElementImpl implements DExpSimpleLambda{

    public DExpSimpleLambdaImpl(ASTNode node) {
        super(node);
    }


}
