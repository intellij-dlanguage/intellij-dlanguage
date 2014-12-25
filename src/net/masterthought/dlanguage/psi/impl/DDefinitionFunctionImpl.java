package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import net.masterthought.dlanguage.psi.DVisitor;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionFunction;
import net.masterthought.dlanguage.psi.interfaces.DElementTypes;
import org.jetbrains.annotations.NotNull;

public class DDefinitionFunctionImpl extends DCompositeElementImpl implements DDefinitionFunction {

    public DDefinitionFunctionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DVisitor) ((DVisitor) visitor).visitDDefinitionFunction(this);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public PsiElement getSymbol() {
        return findNotNullChildByType(DElementTypes.SYMBOL);
    }

}
