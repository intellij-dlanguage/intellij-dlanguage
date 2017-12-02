package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageCastQualifier extends PsiElement {

    @Nullable
    public PsiElement getKW_IMMUTABLE();

    @Nullable
    public PsiElement getKW_CONST();

    @Nullable
    public PsiElement getKW_SHARED();

    @Nullable
    public PsiElement getKW_INOUT();

}
