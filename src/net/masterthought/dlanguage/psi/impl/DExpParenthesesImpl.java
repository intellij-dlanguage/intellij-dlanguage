package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DExpParentheses;

public class DExpParenthesesImpl extends DCompositeElementImpl implements DExpParentheses{

    public DExpParenthesesImpl(ASTNode node) {
        super(node);
    }


}
