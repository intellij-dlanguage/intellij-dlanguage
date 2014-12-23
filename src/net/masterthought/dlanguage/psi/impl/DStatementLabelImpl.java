package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DStatementLabel;

public class DStatementLabelImpl extends DCompositeElementImpl implements DStatementLabel{

    public DStatementLabelImpl(ASTNode node) {
        super(node);
    }


}
