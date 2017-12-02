package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguagePragmaDeclaration extends PsiElement {

    @Nullable
    public DLanguagePragmaExpression getPragmaExpression();

    @Nullable
    public PsiElement getOP_SCOLON();

}
