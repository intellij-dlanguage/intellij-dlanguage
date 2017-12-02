package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageThrowStatement extends PsiElement {

    @Nullable
    public PsiElement getKW_THROW();

    @Nullable
    public DLanguageExpression getExpression();

    @Nullable
    public PsiElement getOP_SCOLON();

}
