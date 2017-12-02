package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageFunctionAttribute extends PsiElement {

    @Nullable
    public DLanguageAtAttribute getAtAttribute();

    @Nullable
    public PsiElement getKW_PURE();

    @Nullable
    public PsiElement getKW_NOTHROW();

}
