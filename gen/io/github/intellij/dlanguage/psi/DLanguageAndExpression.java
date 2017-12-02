package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAndExpression extends PsiElement {

    @Nullable
    public DLanguageAndExpression getAndExpression();

    @Nullable
    public DLanguageCmpExpression getCmpExpression();

    @Nullable
    public PsiElement getOP_AND();

}
