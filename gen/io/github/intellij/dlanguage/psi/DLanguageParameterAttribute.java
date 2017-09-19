package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageParameterAttribute extends PsiElement {
    @Nullable
    PsiElement getKW_FINAL();

    @Nullable
    PsiElement getKW_IN();

    @Nullable
    PsiElement getKW_LAZY();

    @Nullable
    PsiElement getKW_OUT();

    @Nullable
    PsiElement getKW_REF();

    @Nullable
    PsiElement getKW_SCOPE();

    @Nullable
    PsiElement getKW_AUTO();

    @Nullable
    DLanguageTypeConstructor getTypeConstructor();
}
