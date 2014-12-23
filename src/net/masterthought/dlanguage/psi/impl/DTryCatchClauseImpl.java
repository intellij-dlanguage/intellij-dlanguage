package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DTryCatchClause;

public class DTryCatchClauseImpl extends DCompositeElementImpl implements DTryCatchClause{

    public DTryCatchClauseImpl(ASTNode node) {
        super(node);
    }


}
