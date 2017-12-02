package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageFinally extends PsiElement {

    @Nullable
    PsiElement getKW_FINALLY();

    @Nullable
    DLanguageDeclarationOrStatement getDeclarationOrStatement();
}
