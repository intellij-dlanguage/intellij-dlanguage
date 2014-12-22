package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import net.masterthought.dlanguage.psi.DVisitor;
import net.masterthought.dlanguage.psi.interfaces.DElementTypes;
import net.masterthought.dlanguage.psi.interfaces.DModuleDeclaration;
import org.jetbrains.annotations.NotNull;

public class DModuleDeclarationImpl extends DCompositeElementImpl implements DModuleDeclaration {

    public DModuleDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DVisitor) ((DVisitor)visitor).visitModuleDeclaration(this);
        else super.accept(visitor);
    }


    @Override
    @NotNull
    public PsiElement getModule() {
        return findNotNullChildByType(DElementTypes.DECLARATION_MODULE);
    }

}