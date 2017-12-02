package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStaticAssertDeclaration extends PsiElement {

    @Nullable
    public DLanguageStaticAssertStatement getStaticAssertStatement();
}
