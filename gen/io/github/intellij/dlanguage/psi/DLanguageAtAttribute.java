
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAtAttribute extends PsiElement {

    @Nullable
    PsiElement getOP_AT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageArgumentList getArgumentList();

    @Nullable
    DLanguageFunctionCallExpression getFunctionCallExpression();
}
