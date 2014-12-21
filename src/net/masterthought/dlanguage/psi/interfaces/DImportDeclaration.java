package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public interface DImportDeclaration extends DCompositeElement {

    @NotNull
    PsiElement getImport();

}
