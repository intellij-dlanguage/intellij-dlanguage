package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTypeConstructor extends PsiElement {

    @Nullable
    PsiElement getKW_CONST();

    @Nullable
    PsiElement getKW_IMMUTABLE();

    @Nullable
    PsiElement getKW_INOUT();

    @Nullable
    PsiElement getKW_SHARED();

    @Nullable
    PsiElement getKW_SCOPE();

}
