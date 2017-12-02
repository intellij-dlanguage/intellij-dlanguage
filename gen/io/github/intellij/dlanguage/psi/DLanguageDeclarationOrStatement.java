package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDeclarationOrStatement extends PsiElement {

    @Nullable
    public DLanguageStatement getStatement();

    @Nullable
    public DLanguageDeclaration getDeclaration();
}
