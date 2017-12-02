package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageParameterAttribute extends PsiElement {

    @Nullable
    public PsiElement getKW_FINAL();

    @Nullable
    public PsiElement getKW_IN();

    @Nullable
    public PsiElement getKW_LAZY();

    @Nullable
    public PsiElement getKW_OUT();

    @Nullable
    public PsiElement getKW_REF();

    @Nullable
    public PsiElement getKW_SCOPE();

    @Nullable
    public PsiElement getKW_AUTO();

    @Nullable
    public DLanguageTypeConstructor getTypeConstructor();
}
