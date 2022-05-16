package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public interface DLanguageAliasAssign extends PsiElement {

    @NotNull
    DLanguageType getType();

    @NotNull
    PsiElement getOP_EQ();
}
