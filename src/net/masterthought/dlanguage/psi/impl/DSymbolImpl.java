package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import net.masterthought.dlanguage.psi.DVisitor;
import net.masterthought.dlanguage.psi.interfaces.DSymbol;
import org.jetbrains.annotations.NotNull;

public class DSymbolImpl extends DCompositeElementImpl implements DSymbol{

    public DSymbolImpl(ASTNode node) {
        super(node);
    }


}
