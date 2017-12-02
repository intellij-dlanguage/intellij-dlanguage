package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageFunctionBody extends PsiElement {

    @Nullable
    public DLanguageBlockStatement getBlockStatement();

    @Nullable
    public DLanguageInStatement getInStatement();

    @Nullable
    public DLanguageOutStatement getOutStatement();

    @Nullable
    public DLanguageBodyStatement getBodyStatement();
}
