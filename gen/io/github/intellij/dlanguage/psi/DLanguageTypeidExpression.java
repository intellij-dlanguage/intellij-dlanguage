
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTypeidExpression extends PsiElement {

    @Nullable
    DLanguageExpression getExpression();

    @Nullable
    PsiElement getKW_TYPEID();

    @Nullable
    DLanguageType getType();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

}
