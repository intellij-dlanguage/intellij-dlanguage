package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageFinally extends PsiElement {

    @Nullable
    public PsiElement getKW_FINALLY();

    @Nullable
    public DLanguageDeclarationOrStatement getDeclarationOrStatement();
}
