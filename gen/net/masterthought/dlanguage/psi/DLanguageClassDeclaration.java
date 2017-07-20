package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageClassDeclaration extends PsiElement {
    @Nullable
    PsiElement getKW_CLASS();

    @Nullable
    DLanguageInterfaceOrClass getInterfaceOrClass();
}
