package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageConstraint extends PsiElement {
    @Nullable
    PsiElement getKW_IF();

    @Nullable
    DLanguageExpression getExpression();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

}
