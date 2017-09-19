package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageFunctionAttribute extends PsiElement {
    @Nullable
    DLanguageAtAttribute getAtAttribute();

    @Nullable
    PsiElement getKW_PURE();

    @Nullable
    PsiElement getKW_NOTHROW();

}
