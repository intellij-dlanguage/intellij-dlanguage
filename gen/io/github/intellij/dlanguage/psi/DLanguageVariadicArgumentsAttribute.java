package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

public interface DLanguageVariadicArgumentsAttribute extends PsiElement {

    @Nullable
    PsiElement getKW_CONST();

    @Nullable
    PsiElement getKW_IMMUTABLE();

    @Nullable
    PsiElement getKW_SHARED();

    @Nullable
    PsiElement getKW_SCOPE();

    @Nullable
    PsiElement getKW_RETURN();
}
