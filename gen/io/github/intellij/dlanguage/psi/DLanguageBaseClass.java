package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageBaseClass extends PsiElement {
    @Nullable
    DLanguageTypeofExpression getTypeofExpression();

    @Nullable
    PsiElement getOP_DOT();

    @Nullable
    DLanguageIdentifierOrTemplateChain getIdentifierOrTemplateChain();
}
