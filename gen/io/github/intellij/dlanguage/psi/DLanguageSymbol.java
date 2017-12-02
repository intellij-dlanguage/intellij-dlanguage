package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageSymbol extends PsiElement {

    @Nullable
    PsiElement getOP_DOT();

    @Nullable
    DLanguageIdentifierOrTemplateChain getIdentifierOrTemplateChain();
}
