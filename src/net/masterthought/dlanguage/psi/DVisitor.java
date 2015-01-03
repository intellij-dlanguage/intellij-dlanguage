package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElementVisitor;
import net.masterthought.dlanguage.psi.impl.DRefIdentifierImpl;
import net.masterthought.dlanguage.psi.interfaces.*;
import org.jetbrains.annotations.NotNull;

public class DVisitor extends PsiElementVisitor {

    public void visitCompositeElement(@NotNull DCompositeElement o) {
        visitElement(o);
    }

    public void visitDDeclarationModule(DDeclarationModule o) {
        visitCompositeElement(o);
    }

    public void visitDRefModule(DRefModule o) {
        visitCompositeElement(o);
    }

    public void visitDDefinitionVariable(DDefinitionVariable o) {
        visitCompositeElement(o);
    }

    public void visitDRefIdentifier(DRefIdentifier o) {
        visitCompositeElement(o);
    }

    public void visitDDefinitionFunction(DDefinitionFunction o) {
        visitCompositeElement(o);
    }

}
