
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageNonVoidInitializer extends PsiElement {

    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    DLanguageArrayInitializer getArrayInitializer();

    @Nullable
    DLanguageStructInitializer getStructInitializer();

    @Nullable
    DLanguageFunctionBody getFunctionBody();
}
