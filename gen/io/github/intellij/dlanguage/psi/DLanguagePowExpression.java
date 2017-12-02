package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguagePowExpression extends PsiElement {

    @Nullable
    public DLanguagePowExpression getPowExpression();

    @Nullable
    public DLanguageUnaryExpression getUnaryExpression();

    @Nullable
    public PsiElement getOP_POW();

}
