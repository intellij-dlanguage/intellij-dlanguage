package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDeclarationOrStatement extends PsiElement {
    @Nullable
    DLanguageStatement getStatement();

    @Nullable
    DLanguageDeclaration getDeclaration();
}
