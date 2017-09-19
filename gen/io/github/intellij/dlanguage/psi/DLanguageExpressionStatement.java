package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageExpressionStatement extends PsiElement {
    @Nullable
    DLanguageExpression getExpression();

    @Nullable
    PsiElement getOP_SCOLON();

}
