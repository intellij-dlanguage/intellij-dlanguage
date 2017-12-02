package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageInitializer extends PsiElement {

    @Nullable
    public PsiElement getKW_VOID();

    @Nullable
    public DLanguageNonVoidInitializer getNonVoidInitializer();
}
