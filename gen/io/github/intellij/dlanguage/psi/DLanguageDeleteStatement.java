package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDeleteStatement extends PsiElement {

    @Nullable
    PsiElement getKW_DELETE();

}
