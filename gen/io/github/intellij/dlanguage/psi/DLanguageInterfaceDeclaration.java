package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangInterfaceOrClass;
import org.jetbrains.annotations.Nullable;


public interface DLanguageInterfaceDeclaration extends PsiElement {

    @Nullable
    PsiElement getKW_INTERFACE();

    @Nullable
    DlangInterfaceOrClass getInterfaceOrClass();
}
