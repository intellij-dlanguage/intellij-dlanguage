
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageOrExpression extends PsiElement {

    @Nullable
    DLanguageOrExpression getOrExpression();

    @Nullable
    DLanguageXorExpression getXorExpression();

    @Nullable
    PsiElement getOP_OR();

}
