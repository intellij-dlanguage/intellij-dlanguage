package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageThrowStatement extends PsiElement {
    @Nullable
    PsiElement getKW_THROW();

    @Nullable
    DLanguageExpression getExpression();

    @Nullable
    PsiElement getOP_SCOLON();

}
