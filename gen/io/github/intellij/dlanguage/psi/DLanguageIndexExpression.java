
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageIndexExpression extends PsiElement {

    @Nullable
    PsiElement getOP_BRACKET_LEFT();

    @Nullable
    PsiElement getOP_BRACKET_RIGHT();

    @Nullable
    DLanguageArgumentList getArgumentList();

    @Nullable
    DLanguageUnaryExpression getUnaryExpression();
}
