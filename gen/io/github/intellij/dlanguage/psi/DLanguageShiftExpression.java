package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageShiftExpression extends PsiElement {

    @Nullable
    DLanguageShiftExpression getShiftExpression();

    @Nullable
    DLanguageAddExpression getAddExpression();

    @Nullable
    PsiElement getOP_SH_RIGHT();

    @Nullable
    PsiElement getOP_SH_LEFT();

    @Nullable
    PsiElement getOP_USH_RIGHT();

}
