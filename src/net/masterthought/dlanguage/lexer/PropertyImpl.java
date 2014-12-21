package net.masterthought.dlanguage.lexer;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import net.masterthought.dlanguage.psi.DVisitor;
import org.jetbrains.annotations.NotNull;

public class PropertyImpl extends ASTWrapperPsiElement implements Property {

    public PropertyImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DVisitor) ((DVisitor) visitor).visitProperty(this);
        else
            super.accept(visitor);
    }

    public String toString(){
        return "PropertyImpl";
    }
}
