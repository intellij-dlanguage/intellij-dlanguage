package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import net.masterthought.dlanguage.psi.DVisitor;
import net.masterthought.dlanguage.psi.interfaces.DDeclarationModule;
import net.masterthought.dlanguage.psi.interfaces.DElementTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DDeclarationModuleImpl extends DCompositeElementImpl implements DDeclarationModule {

    public DDeclarationModuleImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DVisitor) ((DVisitor) visitor).visitDDeclarationModule(this);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public PsiElement getSymbol() {
        return findNotNullChildByType(DElementTypes.SYMBOL);
    }
}
