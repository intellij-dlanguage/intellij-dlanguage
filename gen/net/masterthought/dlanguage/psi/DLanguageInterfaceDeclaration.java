package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageInterfaceDeclaration extends PsiElement {
    @Nullable
    public PsiElement getKW_INTERFACE();

    @Nullable
    public DLanguageInterfaceOrClass getInterfaceOrClass();
}
