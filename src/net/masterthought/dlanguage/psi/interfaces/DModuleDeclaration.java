package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public interface DModuleDeclaration extends DCompositeElement {

    @NotNull
    PsiElement getModule();

}
