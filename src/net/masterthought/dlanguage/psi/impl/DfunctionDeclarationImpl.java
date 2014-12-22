package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import net.masterthought.dlanguage.psi.DVisitor;
import net.masterthought.dlanguage.psi.interfaces.DElementTypes;
import net.masterthought.dlanguage.psi.interfaces.DFunctionDeclaration;
import net.masterthought.dlanguage.psi.interfaces.DImportDeclaration;
import org.jetbrains.annotations.NotNull;

public class DfunctionDeclarationImpl extends DCompositeElementImpl implements DFunctionDeclaration{

    public DfunctionDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DVisitor) ((DVisitor)visitor).visitFunctionDeclaration(this);
        else super.accept(visitor);
    }


    @NotNull
    @Override
    public PsiElement getFunction() {
        return findNotNullChildByType(DElementTypes.DEFINITION_FUNCTION);
    }
}