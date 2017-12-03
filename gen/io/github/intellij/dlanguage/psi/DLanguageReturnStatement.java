
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageReturnStatement extends PsiElement {

    @Nullable
    PsiElement getKW_RETURN();

    @Nullable
    DLanguageExpression getExpression();

    @Nullable
    PsiElement getOP_SCOLON();

}
