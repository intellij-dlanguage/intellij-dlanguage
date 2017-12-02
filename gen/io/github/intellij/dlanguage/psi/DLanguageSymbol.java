package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageSymbol extends PsiElement {

    @Nullable
    public PsiElement getOP_DOT();

    @Nullable
    public DLanguageIdentifierOrTemplateChain getIdentifierOrTemplateChain();
}
