package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDeprecated extends PsiElement {
    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    DLanguageDeprecated getDeprecated();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

}
