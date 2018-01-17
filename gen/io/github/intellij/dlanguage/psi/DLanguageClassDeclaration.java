package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangInterfaceOrClass;
import org.jetbrains.annotations.Nullable;


public interface DLanguageClassDeclaration extends PsiElement {

    @Nullable
    PsiElement getKW_CLASS();

    @Nullable
    DlangInterfaceOrClass getInterfaceOrClass();
}
