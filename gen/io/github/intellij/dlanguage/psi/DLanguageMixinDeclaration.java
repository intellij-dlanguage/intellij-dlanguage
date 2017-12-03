
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageMixinDeclaration extends PsiElement {

    @Nullable
    DLanguageTemplateMixinExpression getTemplateMixinExpression();

    @Nullable
    DLanguageMixinExpression getMixinExpression();

    @Nullable
    PsiElement getOP_SCOLON();

}
