package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import net.masterthought.dlanguage.psi.DVisitor;
import net.masterthought.dlanguage.psi.interfaces.DRefModule;
import org.jetbrains.annotations.NotNull;

public class DRefModuleImpl extends DCompositeElementImpl implements DRefModule{

    public DRefModuleImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
         if (visitor instanceof DVisitor) ((DVisitor) visitor).visitDRefModule(this);
         else super.accept(visitor);
     }
}
