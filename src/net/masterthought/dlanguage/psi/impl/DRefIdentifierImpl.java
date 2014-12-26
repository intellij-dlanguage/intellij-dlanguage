package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import net.masterthought.dlanguage.psi.DVisitor;
import net.masterthought.dlanguage.psi.interfaces.DRefIdentifier;
import org.jetbrains.annotations.NotNull;

public class DRefIdentifierImpl extends DCompositeElementImpl implements DRefIdentifier{

    public DRefIdentifierImpl(ASTNode node) {
        super(node);
    }

}
