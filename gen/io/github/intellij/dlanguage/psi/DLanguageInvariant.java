package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageInvariant extends PsiElement {
    @Nullable
    DLanguageBlockStatement getBlockStatement();

    @Nullable
    PsiElement getKW_INVARIANT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

}
