package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageBlockStatement extends PsiElement {
    @Nullable
    DLanguageDeclarationsAndStatements getDeclarationsAndStatements();

    @Nullable
    PsiElement getOP_BRACES_RIGHT();

    @Nullable
    PsiElement getOP_BRACES_LEFT();

}
