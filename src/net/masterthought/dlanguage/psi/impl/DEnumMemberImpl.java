package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DEnumMember;

public class DEnumMemberImpl extends DCompositeElementImpl implements DEnumMember{

    public DEnumMemberImpl(ASTNode node) {
        super(node);
    }


}
