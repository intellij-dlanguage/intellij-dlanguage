package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public interface DFunctionDeclaration extends DCompositeElement {

    @NotNull
    PsiElement getFunction();

}
