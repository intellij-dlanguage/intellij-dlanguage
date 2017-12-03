
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageMixinTemplateName extends PsiElement {

    @Nullable
    DLanguageTypeofExpression getTypeofExpression();

    @Nullable
    DLanguageIdentifierOrTemplateChain getIdentifierOrTemplateChain();

    @Nullable
    DLanguageSymbol getSymbol();

    @Nullable
    PsiElement getOP_DOT();

}
