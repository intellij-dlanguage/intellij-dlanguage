package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageBaseClass extends PsiElement {

    @Nullable
    public DLanguageTypeofExpression getTypeofExpression();

    @Nullable
    public PsiElement getOP_DOT();

    @Nullable
    public DLanguageIdentifierOrTemplateChain getIdentifierOrTemplateChain();
}
