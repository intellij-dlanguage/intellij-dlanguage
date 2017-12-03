
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageNewExpression extends PsiElement {

    @Nullable
    PsiElement getKW_NEW();

    @Nullable
    DLanguageNewAnonClassExpression getNewAnonClassExpression();

    @Nullable
    DLanguageType getType();

    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    DLanguageArguments getArguments();

    @Nullable
    PsiElement getOP_BRACKET_LEFT();

    @Nullable
    PsiElement getOP_BRACKET_RIGHT();

}
