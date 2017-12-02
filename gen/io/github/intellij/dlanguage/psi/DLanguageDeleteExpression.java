package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDeleteExpression extends PsiElement {

    @Nullable
    public PsiElement getKW_DELETE();

    @Nullable
    public DLanguageUnaryExpression getUnaryExpression();
}
