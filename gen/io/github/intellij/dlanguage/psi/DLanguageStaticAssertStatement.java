package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStaticAssertStatement extends PsiElement {

    @Nullable
    public PsiElement getKW_STATIC();

    @Nullable
    public DLanguageAssertExpression getAssertExpression();

    @Nullable
    public PsiElement getOP_SCOLON();

}
