package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DInOutFunctionBody;

public class DInOutFunctionBodyImpl extends DCompositeElementImpl implements DInOutFunctionBody{

    public DInOutFunctionBodyImpl(ASTNode node) {
        super(node);
    }


}
