package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTypeofExpression extends PsiElement {
    @Nullable
    DLanguageExpression getExpression();

    @Nullable
    PsiElement getKW_TYPEOF();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getKW_RETURN();

}
