package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DRefModifier;

public class DRefModifierImpl extends DCompositeElementImpl implements DRefModifier{

    public DRefModifierImpl(ASTNode node) {
        super(node);
    }


}
