package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStaticCtorDtorCommon extends PsiElement {

    @Nullable
    public DLanguageFunctionBody getFunctionBody();
}
