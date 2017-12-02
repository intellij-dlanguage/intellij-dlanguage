package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageInvariant extends PsiElement {

    @Nullable
    public DLanguageBlockStatement getBlockStatement();

    @Nullable
    public PsiElement getKW_INVARIANT();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

}
