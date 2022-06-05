package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageFunctionBody extends PsiElement {

    @Nullable
    public DLanguageSpecifiedFunctionBody getSpecifiedFunctionBody();

    @Nullable
    public DLanguageMissingFunctionBody getMissingFunctionBody();

    @Nullable
    public DLanguageShortenedFunctionBody getShortenedFunctionBody();
}
