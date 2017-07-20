package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageInterfaceDeclaration extends PsiElement {
    @Nullable
    PsiElement getKW_INTERFACE();

    @Nullable
    DLanguageInterfaceOrClass getInterfaceOrClass();
}
